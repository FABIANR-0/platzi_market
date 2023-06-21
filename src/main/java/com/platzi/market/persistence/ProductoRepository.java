package com.platzi.market.persistence;

import com.platzi.market.domain.Product;
import com.platzi.market.domain.repository.ProductRepository;
import com.platzi.market.persistence.crud.ProductoCrudRepository;
import com.platzi.market.persistence.entity.Producto;
import com.platzi.market.persistence.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductoRepository implements ProductRepository {

    @Autowired
    private ProductoCrudRepository productoCrudRepository;
    @Autowired
    private ProductMapper mapper;
    @Override
    public List<Product> getAll(){
          List<Producto>  productos= (List<Producto>) productoCrudRepository.findAll();
          return mapper.ToProducts(productos);
    }

    @Override
    public Optional<List<Product>> getByCategory(int categoryId) {
         List<Producto> productos=productoCrudRepository.findByIdCategoriaOrderByNombreAsc(categoryId);
         return Optional.of(mapper.ToProducts(productos));
    }

    @Override
    public Optional<List<Product>> getScarseProducts(int quiantity) {
       Optional<List<Producto>>  productos=productoCrudRepository.findBycantidadStockLessThanAndEstado(quiantity, true);
       return productos.map(prods -> mapper.ToProducts(prods));

    }

    @Override
    public Optional<Product> getProduct(int productId) {

       return  productoCrudRepository.findById(productId).map(producto -> mapper.ToProduct(producto));
    }



  /*  public List<Producto> getByCategoria(int idCategoria){
        return  productoCrudRepository.findByIdCategoria(idCategoria);
    }*/

    @Override
    public Product save(Product product){
        Producto producto= mapper.toProducto(product);
        return mapper.ToProduct(productoCrudRepository.save(producto));
    }
    @Override
    public void delete(int productId){
        productoCrudRepository.deleteById(productId);
    }
}
