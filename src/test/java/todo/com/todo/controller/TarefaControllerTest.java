package todo.com.todo.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import todo.com.todo.repository.TarefaRepository;

@SpringBootTest
class TarefaControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private TarefaRepository tarefaRepository;

    @BeforeEach
    @SuppressWarnings("unused")
    void limparBanco() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        tarefaRepository.deleteAll();
    }

    @Test
    void deveCriarTarefaComStatusCreated() throws Exception {
        mockMvc.perform(post("/api/tarefas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nome": "Implementar CRUD",
                                  "descricao": "Criar backend",
                                  "status": "PENDENTE"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.nome").value("Implementar CRUD"))
                .andExpect(jsonPath("$.status").value("PENDENTE"))
                .andExpect(jsonPath("$.dataCriacao").exists())
                .andExpect(jsonPath("$.dataAtualizacao").exists());
    }

    @Test
    void deveRejeitarTarefaSemNome() throws Exception {
        mockMvc.perform(post("/api/tarefas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"descricao\":\"Sem nome\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensagem").exists());
    }

    @Test
    void deveRetornarNotFoundParaIdInexistente() throws Exception {
        mockMvc.perform(get("/api/tarefas/99999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem").exists());
    }
}
