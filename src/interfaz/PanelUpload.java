package interfaz;

import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.border.TitledBorder;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PanelUpload extends JPanel {
	private JTextField tfArchivoUpl;
	private JTextField tfPassword;
	private AplicacionSecurity principal;
	
	
	/**
	 * Constructor con parametros para relacionar aplicacion con panel
	 */
	public PanelUpload(AplicacionSecurity principal) {
		this();
		this.principal = principal;
	}

	/**
	 * Create the panel.
	 */
	public PanelUpload() {
		setBorder(new TitledBorder(null, "Subir Archivo", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLUE));
		setLayout(new GridLayout(3, 2, 5, 5));
		
		JLabel lblNombreDelArchivo = new JLabel("Nombre del Archivo");
		lblNombreDelArchivo.setFont(new Font("Tahoma", Font.BOLD, 11));
		add(lblNombreDelArchivo);
		
		tfArchivoUpl = new JTextField();
		add(tfArchivoUpl);
		tfArchivoUpl.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 11));
		add(lblPassword);
		
		tfPassword = new JTextField();
		add(tfPassword);
		tfPassword.setColumns(10);
		
		JButton btnLimpiar = new JButton("Limpiar");
		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				principal.limpiarUploads();
			}
		});
		btnLimpiar.setFont(new Font("Tahoma", Font.PLAIN, 13));
		add(btnLimpiar);
		
		JButton btnSubir = new JButton("Subir");
		btnSubir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				principal.subirArchivo();
			}
		});
		btnSubir.setFont(new Font("Tahoma", Font.BOLD, 13));
		add(btnSubir);

	}
	
	public String getArchivoUpl() {
		return tfArchivoUpl.getText();
	}
	
	public String getPassword() {
		return tfPassword.getText();
	}
	
	public void limpiar() {
		tfArchivoUpl.setText("");
		tfPassword.setText("");
	}

}
