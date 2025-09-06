package sistemaeventos;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String nome;
    private String email;
    private String cidade;
    private String telefone;
    private List<Evento> eventosConfirmados;

    public Usuario(String nome, String email, String cidade, String telefone) {
        this.nome = nome;
        this.email = email;
        this.cidade = cidade;
        this.telefone = telefone;
        this.eventosConfirmados = new ArrayList<>();
    }

    public void confirmarEvento(Evento evento) {
        eventosConfirmados.add(evento);
        System.out.println("Evento confirmado: " + evento.getNome());
    }

    public void cancelarEvento(Evento evento) {
        if (eventosConfirmados.remove(evento)) {
            System.out.println("Participação cancelada: " + evento.getNome());
        } else {
            System.out.println("O usuário não estava inscrito neste evento.");
        }
    }

    public void listarEventosConfirmados() {
        System.out.println("Eventos confirmados de " + nome + ":");
        for (Evento e : eventosConfirmados) {
            System.out.println("- " + e.getNome() + " em " + e.getEndereco());
        }
        if (eventosConfirmados.isEmpty()) {
            System.out.println("Nenhum evento confirmado.");
        }
    }
}
