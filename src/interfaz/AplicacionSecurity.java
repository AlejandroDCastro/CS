package interfaz;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import seguridad.CopiaRemota;

public class AplicacionSecurity extends JFrame {

	private JPanel contentPane;
	private PanelUpload panelUpload;
	private PanelDownload panelDownload;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AplicacionSecurity frame = new AplicacionSecurity();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AplicacionSecurity() {
		setResizable(false);
		setTitle("iSecurity");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 255);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		panelUpload = new PanelUpload(this);
		contentPane.add(panelUpload, BorderLayout.NORTH);
		
		panelDownload = new PanelDownload(this);
		contentPane.add(panelDownload, BorderLayout.CENTER);
	}

	public void limpiarUploads() {
		panelUpload.limpiar();
		
	}

	public void subirArchivo() {
		String nombre = panelUpload.getArchivoUpl();
		String pass = panelUpload.getPassword();
		String[] args = {nombre, "E", pass};
		
		CopiaRemota.main(args);
	}

	public void limpiarDownloads() {
		panelDownload.limpiar();
		
	}

	public void descargarArchivo() {
		String nombre = panelDownload.getArchivoDwn();
		String pass = panelDownload.getPassword();
		String[] args = {nombre, "D", pass};
		
		CopiaRemota.main(args);
	}

}
