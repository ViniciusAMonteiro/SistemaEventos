package sistemaeventos;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String arquivo = "events.data";

        // Carregar eventos do arquivo
        List<Evento> listaEventos = carregarEventos(arquivo);

        // Se não tiver eventos no arquivo, criar alguns de teste
        if (listaEventos.isEmpty()) {
            listaEventos.add(new Evento("Show de Rock", "Praça Central", "Show",
                    LocalDateTime.of(2025, 9, 10, 20, 0), "Banda XYZ se apresentando ao vivo"));
            listaEventos.add(new Evento("Festa de Faculdade", "Campus Universitário", "Faculdade",
                    LocalDateTime.of(2025, 9, 12, 18, 0), "Festa de recepção dos calouros"));
            listaEventos.add(new Evento("Partida de Futebol", "Estádio Municipal", "Esporte",
                    LocalDateTime.of(2025, 9, 11, 16, 0), "Campeonato Estadual"));
        }

        // Ordenar eventos pelo horário
        listaEventos.sort(Comparator.comparing(Evento::getHorario));

        System.out.println("----- Lista de Eventos -----");
        for (Evento e : listaEventos) {
            e.exibirInfo();
        }

        // Verificar eventos ocorrendo agora (duração 2h)
        LocalDateTime agora = LocalDateTime.now();
        for (Evento e : listaEventos) {
            if (!agora.isBefore(e.getHorario()) && agora.isBefore(e.getHorario().plusHours(2))) {
                System.out.println("O evento '" + e.getNome() + "' está acontecendo agora!");
            }
        }

        // Mostrar eventos já ocorridos
        System.out.println("----- Eventos já ocorridos -----");
        for (Evento e : listaEventos) {
            if (e.getHorario().isBefore(agora)) {
                System.out.println("- " + e.getNome() + " em " + e.getEndereco());
            }
        }

        // Criar usuário
        Usuario usuario1 = new Usuario("Vinicius Areia", "vinicius@email.com", "Rio de Janeiro", "21999999999");

        // Confirmar presença em eventos
        usuario1.confirmarEvento(listaEventos.get(0));
        usuario1.confirmarEvento(listaEventos.get(2));

        // Listar eventos confirmados
        System.out.println("----- Eventos Confirmados pelo Usuário -----");
        usuario1.listarEventosConfirmados();

        // Cancelar participação em um evento
        usuario1.cancelarEvento(listaEventos.get(2));

        // Listar novamente
        System.out.println("----- Eventos Confirmados Atualizados -----");
        usuario1.listarEventosConfirmados();

        // Salvar eventos no arquivo
        salvarEventos(listaEventos, arquivo);
    }

    // Salvar eventos em arquivo
    public static void salvarEventos(List<Evento> eventos, String arquivo) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo))) {
            for (Evento e : eventos) {
                bw.write(e.getNome() + ";" + e.getEndereco() + ";" + e.getCategoria() + ";" +
                        e.getHorario() + ";" + e.getDescricao());
                bw.newLine();
            }
            System.out.println("Eventos salvos com sucesso!");
        } catch (IOException ex) {
            System.out.println("Erro ao salvar eventos: " + ex.getMessage());
        }
    }

    // Carregar eventos do arquivo
    public static List<Evento> carregarEventos(String arquivo) {
        List<Evento> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length == 5) {
                    Evento e = new Evento(dados[0], dados[1], dados[2],
                            LocalDateTime.parse(dados[3]), dados[4]);
                    lista.add(e);
                }
            }
            System.out.println("Eventos carregados com sucesso!");
        } catch (IOException ex) {
            System.out.println("Arquivo não encontrado. Serão criados eventos de teste.");
        }
        return lista;
    }
}
