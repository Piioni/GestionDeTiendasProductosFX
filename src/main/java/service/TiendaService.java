package service;

import model.Tienda;
import repository.TiendaRepository;

import java.util.List;

public class TiendaService {
    private final TiendaRepository tiendaRepository = new TiendaRepository();


    public void saveTienda(Tienda tienda) {
        // Aquí se puede incluir lógica adicional o validaciones
        tiendaRepository.save(tienda);
    }

    public Tienda getTiendaById(int id) {
        return tiendaRepository.findById(id);
    }

    public List<Tienda> getAllTiendas() {
        return tiendaRepository.findAll();
    }

    public void updateTienda(Tienda tienda) {
        tiendaRepository.update(tienda);
    }

    public void deleteTienda(Tienda tienda) {
        tiendaRepository.delete(tienda);
    }
}