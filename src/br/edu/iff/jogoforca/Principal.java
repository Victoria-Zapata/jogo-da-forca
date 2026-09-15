package br.edu.iff.jogoforca;

import br.edu.iff.bancodepalavras.dominio.palavra.PalavraAppService;
import br.edu.iff.bancodepalavras.dominio.tema.Tema;
import br.edu.iff.bancodepalavras.dominio.tema.TemaFactory;
import br.edu.iff.bancodepalavras.dominio.tema.TemaRepository;
import br.edu.iff.jogoforca.dominio.jogador.Jogador;
import br.edu.iff.jogoforca.dominio.jogador.JogadorFactory;
import br.edu.iff.jogoforca.dominio.jogador.JogadorNaoEncontradoException;
import br.edu.iff.jogoforca.dominio.jogador.JogadorRepository;
import br.edu.iff.jogoforca.dominio.rodada.Rodada;
import br.edu.iff.jogoforca.dominio.rodada.RodadaAppService;
import br.edu.iff.repository.RepositoryException;

import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        Aplicacao app = Aplicacao.getSoleInstance();
        System.out.println("=== APLICAÇÃO DO JOGO DA FORCA CONFIGURADA COM SUCESSO ===");

        Scanner scanner = new Scanner(System.in);

        try {
            // 2) Cria e insere temas
            TemaFactory temaFactory = app.getTemaFactory();
            TemaRepository temaRepo = app.getRepositoryFactory().getTemaRepository();

            Tema temaProg = temaRepo.getPorNome("Programacao");
            if (temaProg == null) {
                temaProg = temaFactory.getTema("Programacao");
                temaRepo.inserir(temaProg);
            }

            Tema temaTec = temaRepo.getPorNome("Tecnologia");
            if (temaTec == null) {
                temaTec = temaFactory.getTema("Tecnologia");
                temaRepo.inserir(temaTec);
            }
            System.out.println("[OK] Temas verificados/inseridos no repositório.");

            // 3) Cadastra palavras
            PalavraAppService palavraApp = PalavraAppService.getSoleInstance();
            palavraApp.novaPalavra("JAVA", temaProg.getId());
            palavraApp.novaPalavra("INTERFACE", temaProg.getId());
            palavraApp.novaPalavra("OBJETO", temaProg.getId());

            palavraApp.novaPalavra("COMPUTADOR", temaTec.getId());
            palavraApp.novaPalavra("INTERNET", temaTec.getId());
            System.out.println("[OK] Palavras cadastradas via PalavraAppService.");

            // 4) Cadastra o Jogador
            JogadorFactory jogadorFactory = app.getJogadorFactory();
            JogadorRepository jogadorRepo = app.getRepositoryFactory().getJogadorRepository();

            String nomeJogador = "Lucas";
            Jogador jogador = jogadorRepo.getPorNome(nomeJogador);
            if (jogador == null) {
                jogador = jogadorFactory.getJogador(nomeJogador);
                jogadorRepo.inserir(jogador);
                System.out.println("[OK] Jogador '" + nomeJogador + "' criado e inserido no repositório.");
            } else {
                System.out.println("[OK] Jogador '" + nomeJogador + "' já cadastrado.");
            }

            // Define o BonecoFactory na classe Rodada para exibir na interface textual
            Rodada.setBonecoFactory(app.getBonecoFactory());

            // 5) Loop Interativo do Jogo da Forca
            RodadaAppService rodadaApp = RodadaAppService.getSoleInstance();
            boolean continuar = true;

            while (continuar) {
                Rodada rodada = rodadaApp.novaRodada(nomeJogador);

                if (rodada == null) {
                    System.out.println("Erro: Não foi possível instanciar a rodada. Verifique se há palavras cadastradas.");
                    break;
                }

                // Loop de palpites da rodada atual
                while (!rodada.encerrou()) {
                    System.out.println("\n==================================================");
                    System.out.println("TEMA: " + rodada.getTema().getNome().toUpperCase());
                    System.out.print("Palavras: ");
                    rodada.exibirPalavras(null);
                    rodada.exibirLetrasErradas(null);
                    System.out.println("Erros: " + rodada.getQtdeErros() + " / " + Rodada.getMaxErros());
                    rodada.exibirBoneco(null);
                    System.out.println("==================================================");

                    System.out.print("Digite uma letra para tentar (ou * para arriscar a palavra): ");
                    String entrada = scanner.nextLine().trim();

                    if (entrada.isEmpty()) {
                        continue;
                    }

                    if (entrada.equals("*")) {
                        System.out.print("Digite o seu palpite para a palavra principal: ");
                        String palpite = scanner.nextLine().trim();
                        rodada.arriscar(new String[]{palpite});
                    } else {
                        char letra = Character.toUpperCase(entrada.charAt(0));
                        rodada.tentar(letra);
                    }
                }

                // Fim da rodada
                System.out.println("\n--------------------------------------------------");
                System.out.println(">>> FIM DA RODADA <<<");
                System.out.print("Palavra(s) secreta(s) eram: ");
                rodada.exibirPalavras(null);

                if (rodada.descobriu()) {
                    int pontosObtidos = rodada.calcularPontos();
                    rodada.getJogador().atualizarPontuacao(pontosObtidos);
                    System.out.println("[PARABÉNS!] Você descobriu as palavras e ganhou " + pontosObtidos + " pontos!");
                } else {
                    System.out.println("[QUE PENA!] Você esgotou suas tentativas ou errou o palpite. O boneco foi pra forca!");
                }
                System.out.println("Pontuação total atual de " + jogador.getNome() + ": " + rodada.getJogador().getPontuacao());
                System.out.println("--------------------------------------------------");

                // Salva a rodada no repositório ao final
                rodadaApp.salvarRodada(rodada);

                System.out.print("\nDeseja jogar novamente? (S/N): ");
                String resposta = scanner.nextLine();
                if (resposta.trim().equalsIgnoreCase("N")) {
                    continuar = false;
                }
            }

        } catch (RepositoryException | JogadorNaoEncontradoException e) {
            System.err.println("Erro durante a execução: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
            System.out.println("\nAplicação encerrada. Obrigado por jogar!");
        }
    }
}