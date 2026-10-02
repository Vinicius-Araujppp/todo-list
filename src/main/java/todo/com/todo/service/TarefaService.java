package todo.com.todo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import todo.com.todo.domain.Tarefa;
import todo.com.todo.exception.TarefaNaoEncontradaException;
import todo.com.todo.repository.TarefaRepository;

@Service
public class TarefaService {

    private final TarefaRepository tarefaRepository;

    public TarefaService(TarefaRepository tarefaRepository) {
        this.tarefaRepository = tarefaRepository;
    }

    @Transactional
    public Tarefa criarTarefa(Tarefa tarefa) {
        return tarefaRepository.save(tarefa);
    }

    @Transactional(readOnly = true)
    public List<Tarefa> listarTarefas() {
        return tarefaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Tarefa buscarTarefaPorId(Long id) {
        return buscarOuLancar(id);
    }

    @Transactional
    public Tarefa atualizarTarefa(Long id, Tarefa tarefaAtualizada) {
        Tarefa tarefa = buscarOuLancar(id);
        tarefa.setNome(tarefaAtualizada.getNome());
        tarefa.setDescricao(tarefaAtualizada.getDescricao());
        tarefa.setStatus(tarefaAtualizada.getStatus());
        tarefa.setObservacoes(tarefaAtualizada.getObservacoes());
        return tarefaRepository.save(tarefa);
    }

    @Transactional
    public void deletarTarefa(Long id) {
        Tarefa tarefa = buscarOuLancar(id);
        tarefaRepository.delete(tarefa);
    }

    private Tarefa buscarOuLancar(Long id) {
        return tarefaRepository.findById(id)
                .orElseThrow(() -> new TarefaNaoEncontradaException(id));
    }
}
