package selina.praxisarbeit.mehrjaehrigkeit;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import selina.praxisarbeit.mehrjaehrigkeit.controller.StartseiteAntragstellerController;

import javax.swing.*;
import java.awt.*;

@SpringBootApplication
public class MehrjaehrigkeitApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = new SpringApplicationBuilder(MehrjaehrigkeitApplication.class).headless(false).run(args);

		EventQueue.invokeLater(() -> {
			JFrame frame = new JFrame();
			StartseiteAntragstellerController startseite = context.getBean(StartseiteAntragstellerController.class);
			startseite.drawGui(frame);
		});
	}

}
