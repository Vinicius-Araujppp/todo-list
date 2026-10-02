package todo.com.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import todo.com.todo.domain.Tarefa;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
}
