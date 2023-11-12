import com.cliente.instrumentos.presentation.notificaciones.Controller;
import com.cliente.instrumentos.logic.Mediator;
import com.cliente.instrumentos.logic.ClienteServidorHandler;
import com.compartidos.elementosCompartidos.Protocol;

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
	private static com.cliente.instrumentos.presentation.tipos.Controller tiposController;
	private static com.cliente.instrumentos.presentation.instrumentos.Controller instrumentosController;
	private static com.cliente.instrumentos.presentation.calibraciones.Controller calibracionesController;
	private static Controller notificacionesController;

	private final static ClienteServidorHandler clienteServidorHandler = ClienteServidorHandler.instance();
	private static Mediator mediator;

	public static void main(String[] args) {
		setLookAndFeel();

		setupControllers();
		initializeComponents();
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


		JPanel mensajes = notificacionesController.getView().getPanel();
		mensajes.setBorder(BorderFactory.createTitledBorder("Mensajes"));
		mensajes.setMaximumSize(new Dimension(200, Integer.MAX_VALUE)); // Establecer altura máxima

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

		notificacionesController = new Controller(
				new com.cliente.instrumentos.presentation.notificaciones.View(),
				new com.cliente.instrumentos.presentation.notificaciones.Model()
		);

		mediator = new Mediator(instrumentosController, calibracionesController);
	}

	private static void setupTabs() {
		tabbedPane.setBorder(BorderFactory.createTitledBorder("Mantenimientos"));
		tabbedPane.addTab("Tipos de Instrumento", tiposController.getView().getPanel());
		tabbedPane.addTab("Instrumentos", instrumentosController.getView().getPanel());
		tabbedPane.addTab("Calibraciones", calibracionesController.getView().getPanel());
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
		window.setSize(1280, 720);
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
					clienteServidorHandler.enviarComandoAlServidor(Protocol.CLOSE, null);
				} catch (Exception ex) {
					ex.printStackTrace();
				} finally {
					System.exit(0);
				}
			}
		});
	}


}
