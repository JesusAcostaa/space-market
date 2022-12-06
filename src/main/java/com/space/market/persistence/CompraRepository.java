package com.space.market.persistence;

import com.space.market.domain.Purchase;
import com.space.market.domain.repository.PurchaseRepository;
import com.space.market.persistence.crud.CompraCrudRepository;
import com.space.market.persistence.entity.Compra;
import com.space.market.persistence.mapper.PurchaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CompraRepository implements PurchaseRepository {

    @Autowired
    private PurchaseMapper mapper;

    @Autowired
    private CompraCrudRepository compraCrudRepository;

    @Override
    public List<Purchase> getAll() {
        return mapper.toPurchases((List<Compra>) compraCrudRepository.findAll()) ;
    }

    @Override
    public Optional<List<Purchase>> getByClient(String clienteId) {

        return compraCrudRepository.findByIdCliente(clienteId)
                .map(compras -> mapper.toPurchases(compras));
    }

    @Override
    public Purchase save(Purchase purchase) {
        Compra compra = mapper.toCompra(purchase);
        compra.getProductos().forEach(producto -> producto.setCompra(compra));
        return mapper.toPurchase(compraCrudRepository.save(compra));
    }
}
