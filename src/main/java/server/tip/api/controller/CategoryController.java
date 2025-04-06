package server.tip.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import server.tip.api.dto.response.CategoryResponse;;
import server.tip.application.CategoryService;
import java.util.List;

@RestController
@RequestMapping("/tip/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    // 카테고리 전체 조회
    @GetMapping
    public List<CategoryResponse> getAllCategories() {
        return categoryService.getAll();
    }

}