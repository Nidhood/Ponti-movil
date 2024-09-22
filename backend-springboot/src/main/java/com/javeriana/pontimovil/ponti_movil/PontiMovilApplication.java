package com.javeriana.pontimovil.ponti_movil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PontiMovilApplication {

	protected static final Logger logger = LoggerFactory.getLogger(PontiMovilApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(PontiMovilApplication.class, args);
		logger.info("""

                                                                                                               \s
                  ╔════════════════════════════════════════════════════════════════════════════════════════════╗
                  ║                                                                                            ║
                  ║   ██████╗  ██████╗ ███╗   ██╗████████╗██╗        ███╗   ███╗ ██████╗ ██╗   ██╗██╗██╗       ║
                  ║   ██╔══██╗██╔═══██╗████╗  ██║╚══██╔══╝██║        ████╗ ████║██╔═══██╗██║   ██║██║██║       ║
                  ║   ██████╔╝██║   ██║██╔██╗ ██║   ██║   ██║ █████╗ ██╔████╔██║██║   ██║██║   ██║██║██║       ║
                  ║   ██╔═══╝ ██║   ██║██║╚██╗██║   ██║   ██║ ╚════╝ ██║╚██╔╝██║██║   ██║╚██╗ ██╔╝██║██║       ║
                  ║   ██║     ╚██████╔╝██║ ╚████║   ██║   ██║        ██║ ╚═╝ ██║╚██████╔╝ ╚████╔╝ ██║███████╗  ║
                  ║   ╚═╝      ╚═════╝ ╚═╝  ╚═══╝   ╚═╝   ╚═╝        ╚═╝     ╚═╝ ╚═════╝   ╚═══╝  ╚═╝╚══════╝  ║
                  ║                                                                                            ║
                  ╚════════════════════════════════════════════════════════════════════════════════════════════╝
                                          🚀 APPLICATION LAUNCHED AND READY TO SERVE! 🚀                         \
                """);
	}
}