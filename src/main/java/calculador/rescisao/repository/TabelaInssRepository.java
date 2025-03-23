package calculador.rescisao.repository;

import calculador.rescisao.model.TabelaInss;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository //repositório Spring
public interface TabelaInssRepository extends JpaRepository<TabelaInss, Long> {

    //procurar pelo ano da vigencia! (importante para calculos de anos anteriores)
    List<TabelaInss> findByAnoVigenciaOrderByFaixaInicioAsc(Integer anoVigencia);
}