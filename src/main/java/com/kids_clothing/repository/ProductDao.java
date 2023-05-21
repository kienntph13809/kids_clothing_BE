package com.kids_clothing.repository;

import com.kids_clothing.entity.Categorydetail;
import com.kids_clothing.entity.Product;
import com.kids_clothing.model.request.ProductRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductDao extends JpaRepository<Product, Long> {
    List<Product> findAll();

    Optional<Product> findById(Long id);

    @Query("SELECT p FROM Product p ORDER BY day_update DESC")
    List<Product> findProductNew();

    List<Product> findByCategorydetail(Categorydetail categorydetail);

//    @Query("SELECT p FROM Product p WHERE p.name LIKE %:name%")
//    List<Product> findByNameLike(@Param("name") String name);

    Product saveAndFlush(Product product);

    <S extends Product> List<S> saveAll(Iterable<S> entities);

    void delete(Product product);

    void deleteInBatch(Iterable<Product> product);

    List<Product> findAllByIsDeleteFalse();

    @Query("SELECT p FROM Product p ORDER BY p.day_update DESC")
    List<Product> findByDayNewCreate();

    @Query(value = "SELECT product.name, product.image, product.price, productdetail.idproduct, categorydetail.name "  +
                        "AS categorydetail_name, categorydetail.id AS categorydetail_id, category.name AS category_name, category.id " +
                       "AS category_id, SUM(productdetail.quantity) AS quantity " +
                       "FROM productdetail " +
                        "JOIN product ON product.id = productdetail.idproduct " +
                        "JOIN categorydetail ON product.idcategorydetail = categorydetail.id " +
                        "JOIN category ON categorydetail.idcategory = category.id "  +
                      "WHERE product.name LIKE %:name% "  +
                       "GROUP BY productdetail.idproduct", nativeQuery = true)
    List<ProductRequest> findByNameLike(@Param("name") String name);
    @Query(value = "SELECT product.name, product.image, product.price, productdetail.idproduct, categorydetail.name " +
            "AS categorydetail_name, categorydetail.id " +
            "AS categorydetail_id, category.name " +
            "AS category_name, category.id " +
            "AS category_id, SUM(productdetail.quantity) " +
            "AS quantity, product.description,product.discount ,product.description_detail " +
            " FROM productdetail JOIN product ON product.id = productdetail.idproduct " +
            "JOIN categorydetail ON product.idcategorydetail = categorydetail.id " +
            "JOIN category ON categorydetail.idcategory = category.id " +
            "GROUP BY productdetail.idproduct;" , nativeQuery = true)
    List<ProductRequest> fillallproduc();
    @Query(value = "SELECT product.name, product.image, product.price, productdetail.idproduct, categorydetail.name " +
            "AS categorydetail_name, categorydetail.id AS categorydetail_id, category.name AS category_name, category.id " +
            "AS category_id, SUM(productdetail.quantity) AS quantity " +
            "FROM productdetail " +
            "JOIN product ON product.id = productdetail.idproduct " +
            "JOIN categorydetail ON product.idcategorydetail = categorydetail.id " +
            "JOIN category ON categorydetail.idcategory = category.id " +
            "WHERE product.name LIKE %:name% " +
            "AND categorydetail.name LIKE %:nameCate% "+
            "AND product.price BETWEEN :minPrice AND :maxPrice " +
            "GROUP BY productdetail.idproduct", nativeQuery = true)
    List<ProductRequest> findPriceProduct(@Param("minPrice") String minPrice, @Param("maxPrice") String maxPrice, @Param("name") String name ,@Param("nameCate") String nameCate);

    @Query(value = "SELECT product.name, product.image, product.price, productdetail.idproduct, categorydetail.name " +
            "AS categorydetail_name, categorydetail.id AS categorydetail_id, category.name AS category_name, category.id " +
            "AS category_id, SUM(productdetail.quantity) AS quantity " +
            "FROM productdetail " +
            "JOIN product ON product.id = productdetail.idproduct " +
            "JOIN categorydetail ON product.idcategorydetail = categorydetail.id " +
            "JOIN category ON categorydetail.idcategory = category.id " +
            "WHERE categorydetail.name LIKE %:nameCate% "+
            "GROUP BY productdetail.idproduct", nativeQuery = true)
    List<ProductRequest> findByDropdown(@Param("nameCate")String nameCate);
    @Query(value = "SELECT product.name, product.image, product.price, productdetail.idproduct, categorydetail.name " +
            "AS categorydetail_name, categorydetail.id AS categorydetail_id, category.name AS category_name, category.id " +
            "AS category_id, SUM(productdetail.quantity) AS quantity " +
            "FROM productdetail " +
            "JOIN product ON product.id = productdetail.idproduct " +
            "JOIN categorydetail ON product.idcategorydetail = categorydetail.id " +
            "JOIN category ON categorydetail.idcategory = category.id " +
            "WHERE categorydetail.name LIKE %:nameCate% "+
            "AND product.price BETWEEN :minPrice AND :maxPrice"+
            "GROUP BY productdetail.idproduct", nativeQuery = true)
    List<ProductRequest> findByDropdownAndPrice(@Param("nameCate")String nameCate , @Param("minPrice")String minPrice , @Param("maxPrice")String max);
    @Query(value = "SELECT product.name, product.image, product.price, productdetail.idproduct, categorydetail.name " +
            "AS categorydetail_name, categorydetail.id AS categorydetail_id, category.name AS category_name, category.id " +
            "AS category_id, SUM(productdetail.quantity) AS quantity " +
            "FROM productdetail " +
            "JOIN product ON product.id = productdetail.idproduct " +
            "JOIN categorydetail ON product.idcategorydetail = categorydetail.id " +
            "JOIN category ON categorydetail.idcategory = category.id " +
            "WHERE categorydetail.name LIKE %:nameCate% "+
            "AND product.name LIKE %:name% "+
            "GROUP BY productdetail.idproduct", nativeQuery = true)
    List<ProductRequest> findByDropdownAndName(@Param("nameCate")String nameCate,@Param("name")String name);
    @Query(value = "SELECT product.name, product.image, product.price, productdetail.idproduct, categorydetail.name " +
            "AS categorydetail_name, categorydetail.id AS categorydetail_id, category.name AS category_name, category.id " +
            "AS category_id, SUM(productdetail.quantity) AS quantity " +
            "FROM productdetail " +
            "JOIN product ON product.id = productdetail.idproduct " +
            "JOIN categorydetail ON product.idcategorydetail = categorydetail.id " +
            "JOIN category ON categorydetail.idcategory = category.id " +
            "WHERE categorydetail.name LIKE %:nameCate% "+
            "AND product.price BETWEEN :maxPrice"+
            "GROUP BY productdetail.idproduct", nativeQuery = true)
    List<ProductRequest> findByDropdownAndMaxprice(@Param("nameCate")String nameCate,@Param("maxPrice")String maxPrice);


}
