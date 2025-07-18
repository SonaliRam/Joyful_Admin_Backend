package com.joyful.serviceImpl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.joyful.entity.Category;
import com.joyful.entity.Product;
import com.joyful.entity.Subcategory;
import com.joyful.repository.CategoryRepository;
import com.joyful.repository.ProductRepository;
import com.joyful.repository.SubcategoryRepository;
import com.joyful.service.CsvImportService;
import com.joyful.util.CsvImportResult;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CsvImportServiceImpl implements CsvImportService {

    private final CategoryRepository categoryRepository;
    private final SubcategoryRepository subcategoryRepository;
    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper; // Add Jackson ObjectMapper

    @Override
    public CsvImportResult parseAndImport(MultipartFile file) {
        CsvImportResult result = new CsvImportResult();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {

            String header = reader.readLine(); // Skip header
            if (header == null) {
                result.addSkippedRow(1, "Header row is missing in CSV file.");
                result.setSuccess(false);
                return result;
            }

            String line;
            int rowNum = 1;

            while ((line = reader.readLine()) != null) {
                rowNum++;
                result.setSuccessRows(result.getSuccessRows()); // optional, or track total elsewhere

                try {
                    String[] fields = line.split(",", -1); // Preserve empty fields

                    if (fields.length < 26) {
                        result.addSkippedRow(rowNum, "Missing required fields. Expected 26 columns, got " + fields.length);
                        continue;
                    }

                    // --- Extract Category fields ---
                    String catName = fields[0].trim();
                    String catDesc = fields[1].trim();
                    String catSearch = fields[2].trim();
                    String catImage = fields[3].trim();
                    String catSeoTitle = fields[4].trim();
                    String catSeoKeywords = fields[5].trim();
                    String catSeoDesc = fields[6].trim();
                    boolean catPublished = Boolean.parseBoolean(fields[7].trim());

                    // --- Extract Subcategory fields ---
                    String subName = fields[8].trim();
                    String subImage = fields[9].trim();
                    String subMetaTitle = fields[10].trim();
                    boolean subPublished = Boolean.parseBoolean(fields[11].trim());
                    String subDesc = fields[12].trim();
                    String subMetaDesc = fields[13].trim();
                    String subKeywords = fields[14].trim();

                    // --- Extract Product fields ---
                    String prodName = fields[15].trim();
                    String prodDesc = fields[16].trim();
                    String prodMainImage = fields[17].trim();
                    String prodTags = fields[18].trim();
                    String prodFilter = fields[19].trim();
                    String prodMetaTitle = fields[20].trim();
                    String prodMetaDesc = fields[21].trim();
                    String prodKeywords = fields[22].trim();
                    boolean prodPublished = Boolean.parseBoolean(fields[23].trim());
                    boolean prodNewArrival = Boolean.parseBoolean(fields[24].trim());
                    String prodVariantsMap = fields[25].trim();

                    // Validate ProductVariantsMap JSON
                 
                 // Update the validation block:
                    if (!prodVariantsMap.isEmpty()) {
                        try {
                            if (prodVariantsMap.trim().startsWith("{") && prodVariantsMap.trim().endsWith("}")) {
                                objectMapper.readTree(prodVariantsMap);
                            } else {
                                // Try to convert simple values to JSON
                                String fixedJson = "{\"value\":\"" + prodVariantsMap + "\"}";
                                objectMapper.readTree(fixedJson);
                                prodVariantsMap = fixedJson; // Use the converted format
                            }
                        } catch (Exception e) {
                            String suggestion = " (Should be JSON format like {\"color\":[\"red\"]} or {\"size\":\"XL\"})";
                            result.addSkippedRow(rowNum, "Invalid ProductVariantsMap: " + e.getMessage().split(" at ")[0] + suggestion);
                            continue;
                        }
                    }

                    // --- Category ---
                    Category category = categoryRepository.findByNameIgnoreCase(catName).orElseGet(() -> {
                        Category c = new Category();
                        c.setName(catName);
                        c.setDescription(catDesc);
                        c.setSearchkeywords(catSearch);
                        c.setImagelink(catImage);
                        c.setSeotitle(catSeoTitle);
                        c.setSeokeywords(catSeoKeywords);
                        c.setSeodescription(catSeoDesc);
                        c.setPublished(catPublished);
                        return categoryRepository.save(c);
                    });

                    // --- Subcategory ---
                    Subcategory subcategory = subcategoryRepository
                            .findByNameAndCategoriesContaining(subName, category)
                            .orElseGet(() -> {
                                Subcategory sub = new Subcategory();
                                sub.setName(subName);
                                sub.setImagepath(subImage);
                                sub.setMetatitle(subMetaTitle);
                                sub.setIspublished(subPublished);
                                sub.setDescription(subDesc);
                                sub.setMetadescription(subMetaDesc);
                                sub.setSeokeywords(subKeywords);
                                sub.setCategories(List.of(category));
                                return subcategoryRepository.save(sub);
                            });

                    // --- Product ---
                    Product product = productRepository
                            .findByNameAndSubcategoriesContaining(prodName, subcategory)
                            .orElse(null);

                    if (product == null) {
                        product = new Product();
                        product.setName(prodName);
                        product.setDescription(prodDesc);
                        product.setMainimage(prodMainImage);
                        product.setHoverimage(prodMainImage); // Duplicate for now
                        product.setProducttags(Arrays.asList(prodTags.split(";")));
                        product.setFilter(prodFilter);
                        product.setMetatitle(prodMetaTitle);
                        product.setMetadescription(prodMetaDesc);
                        product.setPagekeywords(prodKeywords);
                        product.setIspublished(prodPublished);
                        product.setNewarrival(prodNewArrival);
                        product.setVariantsMap(prodVariantsMap);
                        product.setSubcategories(Set.of(subcategory));
                        productRepository.save(product);
                    }

                    result.setSuccessRows(result.getSuccessRows() + 1);
                } catch (Exception e) {
                    result.addSkippedRow(rowNum, "Row failed: " + e.getMessage());
                    e.printStackTrace(); // Optional: helpful in development
                }
            }

            result.setSuccess(result.getSuccessRows() > 0);

        } catch (Exception e) {
            result.addSkippedRow(0, "Failed to read CSV: " + e.getMessage());
            result.setSuccess(false);
        }

        return result;
    }

    @Override
    public CsvImportResult importCsv(MultipartFile file) throws Exception {
        return parseAndImport(file);
    }
}