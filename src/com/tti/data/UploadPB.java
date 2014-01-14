package com.tti.data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.UUID;

import com.tti.entidad.ArchivoDemo;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.ProgressIndicator;
import com.vaadin.ui.Table;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FailedEvent;
import com.vaadin.ui.Upload.FinishedEvent;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.StartedEvent;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class UploadPB extends VerticalLayout {

    private Label state = new Label();
    private Label result = new Label();
    private Label fileName = new Label();
    private Label textualProgress = new Label();
    private static Embedded PDF = new Embedded("Archivo Subido"); 
    private ProgressIndicator pi = new ProgressIndicator();
    private Table tablaArchivos;
    private LineBreakCounter counter = new LineBreakCounter();

    private Upload upload = new Upload(null, counter);
	private BeanItemContainer<ArchivoDemo> container;

    public UploadPB(boolean esAlumno) {
    	if(!esAlumno){
	        initDemoContainer(5);
    	}else{
	    	initUpload();
	    	initDemoContainer(3);
    	}
    }
    
    void initUpload(){
    	setSpacing(true);

        addComponent(new Label(
                "A continuación seleccione el archivo que desea subir"));

        // make analyzing start immediatedly when file is selected
        upload.setImmediate(false);
        upload.setButtonCaption("Subir Archivo");
        addComponent(upload);
        counter.setSlow(true);
        final Button cancelProcessing = new Button("Cancelar");
        cancelProcessing.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                upload.interruptUpload();
            }
        });
        cancelProcessing.setVisible(false);
        cancelProcessing.setStyleName("small");

        final Panel p = new Panel("Estado de la subida");
        p.setVisible(false);
        p.setSizeUndefined();
        FormLayout l = new FormLayout();
        l.setMargin(true);
        p.setContent(l);
        HorizontalLayout stateLayout = new HorizontalLayout();
        stateLayout.setSpacing(true);
        stateLayout.addComponent(state);
        stateLayout.addComponent(cancelProcessing);
        stateLayout.setCaption("Estado");
        state.setValue("Esperando");
        l.addComponent(stateLayout);
        fileName.setCaption("Nombre de Achivo");
        l.addComponent(fileName);
        result.setCaption("Resultado");
        l.addComponent(result);
        result.setVisible(false);
        pi.setCaption("Progreso");
        pi.setVisible(false);
        l.addComponent(pi);
        textualProgress.setVisible(false);
        l.addComponent(textualProgress);
        PDF.setVisible(false);
        addComponent(p);
        addComponent(PDF);
        // Create uploads directory
        File uploads = new File("/uploads");
        if (!uploads.exists() && !uploads.mkdir())
            addComponent(new Label("ERROR: NO SE PUEDE CREAR DIRECTORIO DE SUBIDAS"));
        
        upload.addStartedListener(new Upload.StartedListener() {
            public void uploadStarted(StartedEvent event) {
                // this method gets called immediatedly after upload is
                // started
            	state.setVisible(true);
            	result.setVisible(false);
            	p.setVisible(true);
                pi.setValue(0f);
                pi.setVisible(true);
                pi.setPollingInterval(500); // hit server frequantly to get
                textualProgress.setVisible(true);
                // updates to client
                state.setValue("Subiendo");
                fileName.setValue(event.getFilename());

                cancelProcessing.setVisible(true);
            }
        });

        upload.addProgressListener(new Upload.ProgressListener() {
            public void updateProgress(long readBytes, long contentLength) {
                // this method gets called several times during the update
                pi.setValue(new Float(readBytes / (float) contentLength));
                textualProgress.setValue("Procesado " + readBytes
                        + " bytes de " + contentLength);
//                result.setValue(counter.getLineBreakCount() + " (contando...)");
            }

        });
        upload.addSucceededListener(new Upload.SucceededListener() {
            public void uploadSucceeded(SucceededEvent event) {
                // result.setValue(counter.getLineBreakCount() + " (total)");
            	result.setValue("¡Archivo subido exitosamente!");
            	state.setVisible(false);
            	result.setVisible(true);
            	
            }
        });

        upload.addFailedListener(new Upload.FailedListener() {
            public void uploadFailed(FailedEvent event) {
                result.setValue("Subida interrumpida en el "
                        + Math.round(100 * (Float) pi.getValue()) + "%");
            	result.setVisible(true);
            }
        });

        upload.addFinishedListener(new Upload.FinishedListener() {
            public void uploadFinished(FinishedEvent event) {
            	state.setVisible(true);
            	result.setVisible(true);
            	state.setValue("Finalizado");
                pi.setVisible(false);
                textualProgress.setVisible(false);
                cancelProcessing.setVisible(false);
                String identificador = UUID.randomUUID().toString();
				String nombre = (event.getFilename() + "_" +  identificador);
				String mimeType = event.getMIMEType();
				ArchivoDemo archivo = new ArchivoDemo(container.size(), nombre, "/tmp/archivos", mimeType, new Date());
				container.addBean(archivo);
            }
        });
    }
    
    private void initDemoContainer(int cantidad){
		container = new BeanItemContainer<ArchivoDemo>(ArchivoDemo.class);
		tablaArchivos = new Table("Mis archivos", container);
		tablaArchivos.setColumnHeader("id", "ID");
		tablaArchivos.setColumnHeader("nombre", "Nombre Archivo");
		tablaArchivos.setColumnHeader("fecha", "fecha");
		tablaArchivos.setColumnHeader("tipo", "Extensión");
		tablaArchivos.setVisibleColumns(new String[] { "id", "nombre", "fecha", "tipo"});
		if(cantidad > 0){
			for(int i = 0; i < cantidad; i++){
				String identificador = UUID.randomUUID().toString();
				String nombre = ("Archivo_" + identificador);
				ArchivoDemo archivo = new ArchivoDemo(i, nombre, "/tmp/archivos", "PDF", new Date());
				container.addBean(archivo);
				container.sort(new Object[]{"id"}, new boolean[]{true});
			}
		}
		addComponent(tablaArchivos);
	}

    public static class LineBreakCounter implements Receiver {

        private String fileName;
        private String mtype;

        private int counter;
        private int total;
        private boolean sleep;
//        public File file;
        /**
         * return an OutputStream that simply counts lineends
         */
        public OutputStream receiveUpload(String filename, String MIMEType) {
        	
            counter = 0;
            total = 0;
            fileName = filename;
            mtype = MIMEType;
            return new OutputStream() {
                private static final int searchedByte = '\n';

                @Override
                public void write(int b) throws IOException {
                    total++;
                    if (b == searchedByte) {
                        counter++;
                    }
                    if (sleep && total % 1000 == 0) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            };
            
         // Create upload stream
//            FileOutputStream fos = null; // Stream to write to
//            try {
//                // Open the file for writing.
//                file = new File("/uploads/" + filename);
//                fos = new FileOutputStream(file);
//            } catch (final java.io.FileNotFoundException e) {
//                new Notification("No se puede abrir archivo<br/>",
//                                 e.getMessage(),
//                                 Notification.Type.ERROR_MESSAGE)
//                    .show(Page.getCurrent());
//                return null;
//            }
//            return fos; // Return the output stream to write to
        }
        
//        public void uploadSucceeded(SucceededEvent event) {
//            // Show the uploaded file in the image viewer
//            PDF.setVisible(true);
//            PDF.setSource(new FileResource(file));
//        }

        public String getFileName() {
            return fileName;
        }

        public String getMimeType() {
            return mtype;
        }

        public int getLineBreakCount() {
            return counter;
        }

        public void setSlow(boolean value) {
            sleep = value;
        }
    }
	
	

}
