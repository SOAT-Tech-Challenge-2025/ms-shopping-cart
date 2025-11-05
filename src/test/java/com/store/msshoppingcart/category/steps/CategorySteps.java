package com.store.msshoppingcart.category.steps;

import com.store.msshoppingcart.category.application.service.CategoryServiceImpl;
import com.store.msshoppingcart.category.infrastructure.adapters.in.controller.CategoryController;
import com.store.msshoppingcart.category.infrastructure.adapters.in.dto.CategoryRequestDTO;
import com.store.msshoppingcart.category.infrastructure.adapters.in.dto.CategoryResponseDTO;
import com.store.msshoppingcart.category.infrastructure.adapters.in.dto.CategoryWithProductsDTO;
import com.store.msshoppingcart.category.infrastructure.adapters.in.dto.ProductDTO;
import com.store.msshoppingcart.category.infrastructure.adapters.out.model.CategoryEntity;
import com.store.msshoppingcart.utils.exception.CustomException;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Quando;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.E;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class CategorySteps {

    @Mock
    private CategoryServiceImpl serviceCategory;

    @InjectMocks
    private CategoryController controllerCategory;

    private CategoryRequestDTO categoryDto;
    private ResponseEntity<CategoryResponseDTO> categoryResponse;
    private ResponseEntity<Page<CategoryEntity>> categoriesResponse;
    private ResponseEntity<Optional<CategoryWithProductsDTO>> categoryProductsResponse;
    private Long categoryId;
    private int limit;
    private int offset;
    private int page;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Dado("que quero criar uma categoria com o nome {string}")
    public void queQueroCriarUmaCategoriaComONome(String nomeCategoria) {
        categoryDto = new CategoryRequestDTO(nomeCategoria);
        given(serviceCategory.saveCategory(any(CategoryRequestDTO.class)))
                .willReturn(new CategoryResponseDTO("Categoria criada com sucesso"));
    }

    @Quando("enviar a requisição de criação de categoria")
    public void enviarARequisicaoDeCriacaoDeCategoria() {
        try {
            categoryResponse = controllerCategory.createCategory(categoryDto);
        } catch (CustomException e) {
            categoryResponse = ResponseEntity.status(e.getStatusCode())
                    .body(new CategoryResponseDTO(e.getMessage()));
        }
    }

    @Então("devo receber uma mensagem de {string}")
    public void devoReceberUmaMensagemDe(String mensagemEsperada) {
        assertThat(categoryResponse.getBody()).isNotNull();
        assertThat(categoryResponse.getBody().getMessage()).isEqualTo(mensagemEsperada);
    }

    @E("o status da resposta deve ser {int}")
    public void o_status_da_resposta_deve_ser(Integer statusCode) {
        int actualStatus;
        if (categoryResponse != null) {
            actualStatus = categoryResponse.getStatusCode().value();
        } else if (categoriesResponse != null) {
            actualStatus = categoriesResponse.getStatusCode().value();
        } else if (categoryProductsResponse != null) {
            actualStatus = categoryProductsResponse.getStatusCode().value();
        } else {
            throw new AssertionError("No response available to check status");
        }
        assertThat(actualStatus).isEqualTo(statusCode);
    }

    @Dado("que existe uma categoria cadastrada com o nome {string}")
    public void queExisteUmaCategoriaCadastradaComONome(String nomeCategoria) {
        categoryId = 1L;
        categoryDto = new CategoryRequestDTO(nomeCategoria);
    }

    @E("quero atualizar o nome para {string}")
    public void queroAtualizarONomePara(String novoNome) {
        categoryDto = new CategoryRequestDTO(novoNome);
        given(serviceCategory.updateCategory(anyLong(), any(CategoryRequestDTO.class)))
                .willReturn(new CategoryResponseDTO("Categoria atualizada com sucesso"));
    }

    @Quando("enviar a requisição de atualização de categoria")
    public void enviarARequisicaoDeAtualizacaoDeCategoria() {
        try {
            categoryResponse = controllerCategory.updateCategory(categoryId, categoryDto);
        } catch (Exception e) {
            throw new CustomException(
                    "Erro ao atualizar categoria",
                    BAD_REQUEST,
                    "UPDATE_CATEGORY_ERROR",
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        }
    }

    @Dado("que existe uma categoria cadastrada com id {int}")
    public void queExisteUmaCategoriaCadastradaComId(Integer id) {
        categoryId = id.longValue();
        given(serviceCategory.deleteCategory(anyLong()))
                .willReturn(new CategoryResponseDTO("Categoria excluída com sucesso"));
    }

    @Quando("enviar a requisição de exclusão de categoria")
    public void enviarARequisicaoDeExclusaoDeCategoria() {
        try {
            categoryResponse = controllerCategory.deleteCategory(categoryId);
        } catch (Exception e) {
            throw new CustomException(
                    "Erro ao excluir categoria",
                    BAD_REQUEST,
                    "DELETE_CATEGORY_ERROR",
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        }
    }

    @Dado("que existem {int} categorias cadastradas no sistema")
    public void queExistemCategoriasCadastradasNoSistema(Integer quantidade) {
        List<CategoryEntity> categories = new ArrayList<>();
        for (int i = 1; i <= quantidade; i++) {
            CategoryEntity category = new CategoryEntity();
            category.setId((long) i);
            category.setCategoryName("Categoria " + i);
            category.setDateInclusion(new Date(System.currentTimeMillis()));
            category.setTimestamp(new Timestamp(System.currentTimeMillis()));
            categories.add(category);
        }
        Page<CategoryEntity> categoryPage = new PageImpl<>(categories);
        given(serviceCategory.getAllCategories(any(Pageable.class)))
                .willReturn(categoryPage);
    }

    @Dado("que não existem categorias cadastradas no sistema")
    public void queNaoExistemCategoriasCadastradasNoSistema() {
        Page<CategoryEntity> emptyPage = new PageImpl<>(new ArrayList<>());
        given(serviceCategory.getAllCategories(any(Pageable.class)))
                .willReturn(emptyPage);
    }

    @Quando("solicitar a listagem de categorias com os parâmetros:")
    public void solicitarAListagemDeCategoriasComOsParametros(DataTable dataTable) {
        Map<String, String> params = dataTable.asMap(String.class, String.class);
        limit = Integer.parseInt(params.get("limit"));
        offset = Integer.parseInt(params.get("offset"));
        page = Integer.parseInt(params.get("page"));

        try {
            categoriesResponse = controllerCategory.getAllCategories(limit, offset, page);
        } catch (Exception e) {
            throw new CustomException(
                "Erro ao listar categorias",
                BAD_REQUEST,
                "LIST_CATEGORIES_ERROR",
                LocalDateTime.now(),
                UUID.randomUUID()
            );
        }
    }

    @Então("devo receber uma lista com {int} categorias")
    public void devoReceberUmaListaComCategorias(Integer quantidade) {
        assertThat(categoriesResponse.getBody()).isNotNull();
        assertThat(categoriesResponse.getBody().getContent()).hasSize(quantidade);
    }

    @Então("devo receber uma lista vazia")
    public void devoReceberUmaListaVazia() {
        assertThat(categoriesResponse.getBody()).isNotNull();
        assertThat(categoriesResponse.getBody().getContent()).isEmpty();
    }

    @Dado("que existe uma categoria com id {int} que possui produtos cadastrados")
    public void queExisteUmaCategoriaComIdQuePossuiProdutosCadastrados(Integer id) {
        categoryId = id.longValue();
        CategoryWithProductsDTO categoryWithProducts = new CategoryWithProductsDTO();
        categoryWithProducts.setCategoriaId(id.longValue());
        categoryWithProducts.setNomeCategoria("Categoria Teste");

        List<ProductDTO> produtos = new ArrayList<>();
        ProductDTO produto1 = new ProductDTO();
        produto1.setProductId(1L);
        produto1.setNameProduct("Produto 1");
        produto1.setUnitPrice(new BigDecimal("10.0"));
        produto1.setPreparationTime(30);
        produto1.setDtInclusion(new Date(System.currentTimeMillis()));

        ProductDTO produto2 = new ProductDTO();
        produto2.setProductId(2L);
        produto2.setNameProduct("Produto 2");
        produto2.setUnitPrice(new BigDecimal("20.0"));
        produto2.setPreparationTime(45);
        produto2.setDtInclusion(new Date(System.currentTimeMillis()));

        categoryWithProducts.setProdutos(Arrays.asList(produto1, produto2));

        given(serviceCategory.getProductsByCategoryId(id.longValue()))
                .willReturn(Optional.of(categoryWithProducts));
    }

    @Dado("que existe uma categoria com id {int} sem produtos cadastrados")
    public void queExisteUmaCategoriaComIdSemProdutosCadastrados(Integer id) {
        categoryId = id.longValue();
        CategoryWithProductsDTO categoryWithProducts = new CategoryWithProductsDTO();
        categoryWithProducts.setCategoriaId(id.longValue());
        categoryWithProducts.setNomeCategoria("Categoria Sem Produtos");
        categoryWithProducts.setProdutos(new ArrayList<>());

        given(serviceCategory.getProductsByCategoryId(id.longValue()))
                .willReturn(Optional.of(categoryWithProducts));
    }

    @Dado("que não existe uma categoria com id {int}")
    public void queNaoExisteUmaCategoriaComId(Integer id) {
        categoryId = id.longValue();
        given(serviceCategory.getProductsByCategoryId(id.longValue()))
                .willReturn(Optional.empty());
    }

    @Quando("solicitar a listagem de produtos da categoria")
    public void solicitarAListagemDeProdutosDaCategoria() {
        try {
            categoryProductsResponse = controllerCategory.getProductByCategoryId(categoryId);
        } catch (Exception e) {
            throw new CustomException(
                "Erro ao listar produtos da categoria",
                BAD_REQUEST,
                "LIST_CATEGORY_PRODUCTS_ERROR",
                LocalDateTime.now(),
                UUID.randomUUID()
            );
        }
    }

    @Então("devo receber uma lista de produtos da categoria")
    public void devoReceberUmaListaDeProdutosDaCategoria() {
        assertThat(categoryProductsResponse.getBody()).isPresent();
        CategoryWithProductsDTO category = categoryProductsResponse.getBody().get();
        assertThat(category.getProdutos()).isNotEmpty();
        assertThat(category.getProdutos()).hasSize(2);
    }

    @Então("devo receber uma lista vazia de produtos")
    public void devoReceberUmaListaVaziaDeProdutos() {
        assertThat(categoryProductsResponse.getBody()).isPresent();
        CategoryWithProductsDTO category = categoryProductsResponse.getBody().get();
        assertThat(category.getProdutos()).isEmpty();
    }

    @Então("devo receber uma resposta vazia")
    public void devoReceberUmaRespostaVazia() {
        assertThat(categoryProductsResponse.getBody()).isEmpty();
    }
}
