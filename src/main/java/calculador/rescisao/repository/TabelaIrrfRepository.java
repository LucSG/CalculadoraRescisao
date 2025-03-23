package calculador.rescisao.repository;

import calculador.rescisao.model.TabelaIrrf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository //repositório Spring
public interface TabelaIrrfRepository extends JpaRepository<TabelaIrrf, Long> {

    //busca por ano de vigência (importante para anos anteriores)
    List<TabelaIrrf> findByAnoVigenciaOrderByFaixaInicioAsc(Integer anoVigencia);
}