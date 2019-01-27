package interfaz;

import javax.swing.JPanel;
import java.awt.GridLayout;

import javax.swing.border.TitledBorder;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PanelDownload extends JPanel {
	private JTextField tfArchivoDwn;
	private JTextField tfPassword;
	private AplicacionSecurity principal;
	
	/**
	 * Constructor con parametros para relacionar aplicacion con panel
	 */
	public PanelDownload(AplicacionSecurity principal) {
		this();
		this.principal = principal;
	}
	
	/**
	 * Create the panel.
	 */
	public PanelDownload() {
		setBorder(new TitledBorder(null, "Descargar Archivo", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLUE));
		setLayout(new GridLayout(3, 2, 5, 5));
		
		JLabel lblNombreDelArchivo = new JLabel("Nombre del Archivo");
		lblNombreDelArchivo.setFont(new Font("Tahoma", Font.BOLD, 11));
		add(lblNombreDelArchivo);
		
		tfArchivoDwn = new JTextField();
		add(tfArchivoDwn);
		tfArchivoDwn.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 11));
		add(lblPassword);
		
		tfPassword = new JTextField();
		add(tfPassword);
		tfPassword.setColumns(10);
		
		JButton btnLimpiar = new JButton("Limpiar");
		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				principal.limpiarDownloads();
			}
		});
		btnLimpiar.setFont(new Font("Tahoma", Font.PLAIN, 13));
		add(btnLimpiar);
		
		JButton btnDescargar = new JButton("Descargar");
		btnDescargar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				principal.descargarArchivo();
			}
		});
		btnDescargar.setFont(new Font("Tahoma", Font.BOLD, 13));
		add(btnDescargar);

	}
	
	public String getArchivoDwn() {
		return tfArchivoDwn.getText();
	}
	
	public String getPassword() {
		return tfPassword.getText();
	}
	
	public void limpiar() {
		tfArchivoDwn.setText("");
		tfPassword.setText("");
	}

}
