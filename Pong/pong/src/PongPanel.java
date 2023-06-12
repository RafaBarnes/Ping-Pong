import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import java.awt.Image;


public class PongPanel extends JPanel implements Runnable, KeyListener {
    private static final int LARGURA = 800;
    private static final int ALTURA = 400;
    private static final int LARGURA_RAQUETE = 10;
    private static final int ALTURA_RAQUETE = 60;
    private static final int TAMANHO_BOLA = 20;
    private static final int VELOCIDADE_RAQUETE = 5;
    private static final int VELOCIDADE_BOLA_X = 2;
    private static final int VELOCIDADE_BOLA_Y = 2;
    private static final int TAMANHO_PLACAR = 24;

    private int raquete1Y = ALTURA / 2 - ALTURA_RAQUETE / 2;
    private int raquete2Y = ALTURA / 2 - ALTURA_RAQUETE / 2;
    private int bolaX = LARGURA / 2 - TAMANHO_BOLA / 2;
    private int bolaY = ALTURA / 2 - TAMANHO_BOLA / 2;
    private int velocidadeBolaX = VELOCIDADE_BOLA_X;
    private int velocidadeBolaY = VELOCIDADE_BOLA_Y;
    private int pontuacaoJogador1 = 0;
    private int pontuacaoJogador2 = 0;
    private String nomeJogador1 = "";
    private String nomeJogador2 = "";

    private boolean raquete1Cima = false;
    private boolean raquete1Baixo = false;
    private boolean raquete2Cima = false;
    private boolean raquete2Baixo = false;
    private Image imagemBola;

    public PongPanel() {
        setPreferredSize(new Dimension(LARGURA, ALTURA));
        setBackground(new Color(0, 100, 0));
        setFocusable(true);
        addKeyListener(this);
        setFocusTraversalKeysEnabled(false);

        //Solicitar o nome dos jogadores
        nomeJogador1 = JOptionPane.showInputDialog(null,"Digite o nome do primeiro jogador: ");
        nomeJogador2 = JOptionPane.showInputDialog(null,"Digite o nome do segundo jogador:");

        Thread threadJogo = new Thread(this);
        threadJogo.start();

        // Carregar imagens
        imagemBola = new ImageIcon("bola.png").getImage();
    }


    public void moverRaquete1Cima() {
        if (raquete1Y > 0) {
            raquete1Y -= VELOCIDADE_RAQUETE;
        }
    }

    public void moverRaquete1Baixo() {
        if (raquete1Y < ALTURA - ALTURA_RAQUETE) {
            raquete1Y += VELOCIDADE_RAQUETE;
        }
    }

    public void moverRaquete2Cima() {
        if (raquete2Y > 0) {
            raquete2Y -= VELOCIDADE_RAQUETE;
        }
    }

    public void moverRaquete2Baixo() {
        if (raquete2Y < ALTURA - ALTURA_RAQUETE) {
            raquete2Y += VELOCIDADE_RAQUETE;
        }
    }

    public void moverBola() {
        bolaX += velocidadeBolaX;
        bolaY += velocidadeBolaY;

        if (bolaY <= 0 || bolaY >= ALTURA - TAMANHO_BOLA) {
            velocidadeBolaY = -velocidadeBolaY;
        }

        if (bolaX <= LARGURA_RAQUETE && bolaY + TAMANHO_BOLA >= raquete1Y && bolaY <= raquete1Y + ALTURA_RAQUETE) {
            velocidadeBolaX = -velocidadeBolaX;
        }

        if (bolaX >= LARGURA - LARGURA_RAQUETE - TAMANHO_BOLA && bolaY + TAMANHO_BOLA >= raquete2Y && bolaY <= raquete2Y + ALTURA_RAQUETE) {
            velocidadeBolaX = -velocidadeBolaX;
        }

        if (bolaX <= 0) {
            pontuacaoJogador2++;
            reiniciarBola();
        }

        if (bolaX >= LARGURA - TAMANHO_BOLA) {
            pontuacaoJogador1++;
            reiniciarBola();
        }
    }

    private void reiniciarBola() {
        bolaX = LARGURA / 2 - TAMANHO_BOLA / 2;
        bolaY = ALTURA / 2 - TAMANHO_BOLA / 2;
        velocidadeBolaX = VELOCIDADE_BOLA_X;
        velocidadeBolaY = VELOCIDADE_BOLA_Y;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.WHITE);
        g.fillRect(LARGURA / 2 - 1, 0, 2, ALTURA);

        g.fillOval(bolaX, bolaY, TAMANHO_BOLA, TAMANHO_BOLA);

        g.setColor(Color.WHITE);
        g.fillRect(0, raquete1Y, LARGURA_RAQUETE, ALTURA_RAQUETE);
        g.fillRect(LARGURA - LARGURA_RAQUETE, raquete2Y, LARGURA_RAQUETE, ALTURA_RAQUETE);

        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, TAMANHO_PLACAR));
        FontMetrics fm = g.getFontMetrics();
        String placar1 = Integer.toString(pontuacaoJogador1);
        String placar2 = Integer.toString(pontuacaoJogador2);
        int larguraPlacar1 = fm.stringWidth(placar1);
        int espacamentoPlacar = 10; // Esse valor altera o espaçamento entre os placares

        //Define o tamanho do placar
        g.drawString(placar1, LARGURA / 2 - larguraPlacar1 - espacamentoPlacar, TAMANHO_PLACAR);
        g.drawString(placar2, LARGURA / 2 + espacamentoPlacar, TAMANHO_PLACAR);

        //Define a cor e a fonte do placar
        g.setColor(Color.WHITE);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));

        //Desenha o nome do Jogador 1  e 2
        g.drawString(nomeJogador1, 280, 20); //X E Y ONDE FICARA ESCRITO O NOME
        g.drawString(nomeJogador2, LARGURA - 350, 20); //X E Y ONDE FICARA ESCRITO O NOME

        //Define a cor e a fonte dos nomes dos jogadores
        g.setColor(Color.WHITE);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));


        int tamanhoCirculo = 100;
        int circuloX = getWidth() / 2 - tamanhoCirculo / 2;
        int circuloY = getHeight() / 2 - tamanhoCirculo / 2;
        g.setColor(Color.WHITE);
        g.drawOval(circuloX, circuloY, tamanhoCirculo, tamanhoCirculo);

        g.setColor(Color.BLUE);
        g.fillRect(0, raquete1Y, LARGURA_RAQUETE/2, ALTURA_RAQUETE);

        // Desenha meio quadrado no lado direito
        g.setColor(Color.RED);
        g.fillRect(LARGURA - LARGURA_RAQUETE/2, raquete2Y, LARGURA_RAQUETE/2, ALTURA_RAQUETE);

    }

    @Override
    public void run() {
        while (true) {
            if (raquete1Cima) {
                moverRaquete1Cima();
            }
            if (raquete1Baixo) {
                moverRaquete1Baixo();
            }
            if (raquete2Cima) {
                moverRaquete2Cima();
            }
            if (raquete2Baixo) {
                moverRaquete2Baixo();
            }

            moverBola();
            repaint();

            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            raquete1Cima = true;
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            raquete1Baixo = true;
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            raquete2Cima = true;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            raquete2Baixo = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            raquete1Cima = false;
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            raquete1Baixo = false;
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            raquete2Cima = false;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            raquete2Baixo = false;
        }
    }
}
