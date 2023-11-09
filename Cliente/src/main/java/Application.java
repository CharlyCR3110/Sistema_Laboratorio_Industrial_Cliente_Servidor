import com.compartidos.elementosCompartidos.Instrumento;
import com.cliente.instrumentos.logic.Mediator;
import com.cliente.instrumentos.logic.ClienteServidorHandler;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;

public class Application {
	private static JFrame window;
	private static JTabbedPane tabbedPane;
	private static Instrumento instrumentoSeleccionado; // Se usa en la ventana de calibraciones
	private static com.cliente.instrumentos.presentation.tipos.Controller tiposController;
	private static com.cliente.instrumentos.presentation.instrumentos.Controller instrumentosController;
	private static com.cliente.instrumentos.presentation.calibraciones.Controller calibracionesController;
	private static com.cliente.instrumentos.presentation.notificaciones.NotificacionesController notificacionesController;

	private static Mediator mediator;

	public static void main(String[] args) {
		ClienteServidorHandler clienteServidorHandler = ClienteServidorHandler.instance();
		setLookAndFeel();

		initializeComponents();
		setupControllers();
		setupTabs();
		setupWindow();
		addTabChangeListeners();
		addWindowListener();
	}

	private static void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private static void initializeComponents() {
		window = new JFrame();
		tabbedPane = new JTabbedPane();
		window.setLayout(new BorderLayout());
		window.add(tabbedPane, BorderLayout.CENTER);


		JPanel mensajes = new com.cliente.instrumentos.presentation.notificaciones.View().getPanel();
		mensajes.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		mensajes.setBounds(770, 10, 300, 400);

		window.add(mensajes, BorderLayout.EAST);
	}

	private static void setupControllers() {
		tiposController = new com.cliente.instrumentos.presentation.tipos.Controller(
				new com.cliente.instrumentos.presentation.tipos.View(),
				new com.cliente.instrumentos.presentation.tipos.Model()
		);

		instrumentosController = new com.cliente.instrumentos.presentation.instrumentos.Controller(
				new com.cliente.instrumentos.presentation.instrumentos.View(),
				new com.cliente.instrumentos.presentation.instrumentos.Model()
		);

		calibracionesController = new com.cliente.instrumentos.presentation.calibraciones.Controller(
				new com.cliente.instrumentos.presentation.calibraciones.View(),
				new com.cliente.instrumentos.presentation.calibraciones.Model()
		);

		notificacionesController = new com.cliente.instrumentos.presentation.notificaciones.NotificacionesController(
				new com.cliente.instrumentos.presentation.notificaciones.View()
		);

		mediator = new Mediator(instrumentosController, calibracionesController);
	}

	private static void setupTabs() {
		tabbedPane.addTab("Tipos de Instrumento", tiposController.getView().getPanel());
		tabbedPane.addTab("Instrumentos", instrumentosController.getView().getPanel());
		tabbedPane.addTab("Calibraciones", calibracionesController.getView().getPanel());
//		tabbedPane.addTab("Notificaciones", notificacionesController.getView().getPanel());
		tabbedPane.addTab("Acerca de", new com.cliente.instrumentos.presentation.acercaDe.View().getPanel());
	}

	private static void addTabChangeListeners () {
		tabbedPane.addChangeListener(createTabChangeListener());
		tabbedPane.addChangeListener(createTipoInstrumentoChangeListener());
	}

	private static ChangeListener createTabChangeListener() {
		return new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int selectedIndex = tabbedPane.getSelectedIndex();
				if (selectedIndex == 2) { // Índice 2 corresponde a la pestaña de Calibraciones
					tabbedPane.setSelectedIndex(selectedIndex);
					mediator.setInstrumentoSeleccionado();
				}
			}
		};
	}

	// change listener para cargar los tipos de instrumento en el combo box de la ventana de instrumentos
	public static ChangeListener createTipoInstrumentoChangeListener() {
		return new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int selectedIndex = tabbedPane.getSelectedIndex();
				if (selectedIndex == 1) { // Índice 1 corresponde a la pestaña de Instrumentos
					tabbedPane.setSelectedIndex(selectedIndex);
					System.out.println("Cargando tipos de instrumento");
					instrumentosController.getView().setTipos();
				}
			}
		};
	}

	private static void setupWindow() {
		window.setSize(900, 400);
		window.setResizable(true);
		window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		window.setIconImage(new ImageIcon(Objects.requireNonNull(Application.class.getResource("icon.png"))).getImage());
		window.setTitle("SILAB: Sistema de Laboratorio Industrial");
		window.setVisible(true);
	}

	// Cuando el cliente quiera cerrar la ventana, se cierra la conexión con el servidor
	private static void addWindowListener() {
		window.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					ClienteServidorHandler clienteServidorHandler = ClienteServidorHandler.instance();
					clienteServidorHandler.enviarComandoAlServidor("close", null);
					System.out.println("Cerrando conexión con el servidor");
				} catch (Exception ex) {
					ex.printStackTrace();
				} finally {
					System.exit(0);
				}
			}
		});
	}


}
