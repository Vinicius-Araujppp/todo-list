package todo.com.todo.service;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import todo.com.todo.domain.StatusTarefa;
import todo.com.todo.domain.Tarefa;
import todo.com.todo.exception.TarefaNaoEncontradaException;
import todo.com.todo.repository.TarefaRepository;

@ExtendWith(MockitoExtension.class)
class TarefaServiceTest {

    @Mock
    private TarefaRepository tarefaRepository;

    @InjectMocks
    private TarefaService tarefaService;

    @Test
    void deveCriarTarefa() {
        Tarefa tarefa = tarefa("Estudar Spring");
        when(tarefaRepository.save(tarefa)).thenReturn(tarefa);

        Tarefa resultado = tarefaService.criarTarefa(tarefa);

        assertEquals(tarefa, resultado);
        verify(tarefaRepository).save(tarefa);
    }

    @Test
    void deveLancarExcecaoAoBuscarTarefaInexistente() {
        when(tarefaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(TarefaNaoEncontradaException.class,
                () -> tarefaService.buscarTarefaPorId(99L));
    }

    @Test
    void deveAtualizarTarefaExistente() {
        Tarefa existente = tarefa("Nome antigo");
        Tarefa atualizada = tarefa("Nome novo");
        atualizada.setStatus(StatusTarefa.CONCLUIDA);
        when(tarefaRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(tarefaRepository.save(existente)).thenReturn(existente);

        Tarefa resultado = tarefaService.atualizarTarefa(1L, atualizada);

        assertEquals("Nome novo", resultado.getNome());
        assertEquals(StatusTarefa.CONCLUIDA, resultado.getStatus());
        verify(tarefaRepository).save(existente);
    }

    @Test
    void deveExcluirTarefaExistente() {
        Tarefa tarefa = tarefa("Excluir tarefa");
        when(tarefaRepository.findById(1L)).thenReturn(Optional.of(tarefa));

        tarefaService.deletarTarefa(1L);

        verify(tarefaRepository).delete(tarefa);
    }

    private Tarefa tarefa(String nome) {
        Tarefa tarefa = new Tarefa();
        tarefa.setNome(nome);
        tarefa.setStatus(StatusTarefa.PENDENTE);
        return tarefa;
    }
}
