package ecm.arrubaguest.server;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPTableEvent;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CreatePdf {
    
    String ContextPath = null;
    String user = null;
    String datecreation = null;
    String type = null;
    List<Radcheck> rl = new ArrayList<>();
    
    
    private static final Font bigFont = new Font(Font.FontFamily.TIMES_ROMAN, 20,
            Font.BOLD,BaseColor.DARK_GRAY);
    private static final Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
    private static final Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL, BaseColor.RED);
    private static final Font littleFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD, BaseColor.BLACK);
    private static final Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);
    private static final Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 14,
            Font.BOLD);
    private static final Font underbold = new Font(Font.FontFamily.TIMES_ROMAN,
            14, Font.BOLD | Font.UNDERLINE);
    private static final Font italicfonts = new Font(Font.FontFamily.TIMES_ROMAN,
            12, Font.BOLDITALIC);

    class TableHeader extends PdfPageEventHelper {
        
        public void onStartPage(PdfWriter writer, Document document) {
            
            try {
                Image im = Image.getInstance(ContextPath + "protected/ecm_logo.png");
                Image im2 = Image.getInstance(ContextPath + "protected/wifi_ecm.png");
                Paragraph T = new Paragraph("COMPTE WIFI INVITÉS", bigFont);
                Paragraph in;
                
                im.scalePercent(50f);
                im2.scalePercent(60f);
                
                T.setAlignment(Element.ALIGN_CENTER);
                T.setSpacingBefore(10);
                T.setSpacingAfter(10);
                
                document.add(im);
                im2.setAbsolutePosition(470f , 770f);
                document.add(im2);
                document.add(T);
                
                if ( type.equals("prov") || type.equals("guest")) {
                    in = new Paragraph("(Type Provisioning, Valide 30 jours)", littleFont);
                    in.setAlignment(Element.ALIGN_CENTER);
                    document.add(in);
                }
                
            } catch (BadElementException ex) {
                Logger.getLogger(CreatePdf.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(CreatePdf.class.getName()).log(Level.SEVERE, null, ex);
            } catch (DocumentException ex) {
                Logger.getLogger(CreatePdf.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
        public void onEndPage(PdfWriter writer, Document document) {
                        
            String header = "";
            Phrase ph = new Phrase("Compte généré par : " + user + ", le " + datecreation, //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                    smallBold);
            
              PdfPTable table = new PdfPTable(3);
            try {
                table.setWidths(new int[]{50, 24, 4});
                table.setTotalWidth(527);
                table.setLockedWidth(true);
                table.getDefaultCell().setFixedHeight(20);
                table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                table.addCell(ph);
                table.addCell(header);
                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_MIDDLE);
          //    table.addCell(String.format("Page %d of", writer.getPageNumber()));
                

                PdfPCell cell = new PdfPCell(new Phrase(""));
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);
                table.writeSelectedRows(0, -1, 34, 45, writer.getDirectContent());
            }
            catch(DocumentException de) {
                throw new ExceptionConverter(de);
            }
            
     /*   ColumnText.showTextAligned(writer.getDirectContent(),
                    Element.ALIGN_CENTER, new Phrase(String.format("page %d", writer.getPageNumber())),
                    (rect.getLeft() + rect.getRight()) / 2, rect.getBottom() - 18, 0);
     */
            }
    }
          
    class TableBackground implements PdfPTableEvent {
 
        public void tableLayout(PdfPTable table, float[][] width, float[] height,
                int headerRows, int rowStart, PdfContentByte[] canvas) {
            PdfContentByte background = canvas[PdfPTable.BASECANVAS];
            background.saveState();
            background.setCMYKColorFill(0x00, 0x00, 0xFF, 0x0F);
            background.roundRectangle(
                width[0][0], height[height.length - 1] - 2,
                width[0][1] - width[0][0] + 6, height[0] - height[height.length - 1] - 4, 4);
            background.fill();
            background.restoreState();
        }
    }
    
    public CreatePdf(String ContextPath , List<Radcheck> lr,String user,String date, String type) {
        
        this.ContextPath = ContextPath ;
        this.user = user;
        this.type = type;
        datecreation = date;
        rl = lr;
        System.out.println("Dans createpdf : " + datecreation);
        
    }
 
    private  void addMetaData(Document document) {
    document.addTitle("My first PDF");
    document.addSubject("Using iText");
    document.addKeywords("Java, PDF, iText");
    document.addAuthor("JLF");
    document.addCreator("JLF");
  }
  
    private PdfPCell Create_Cell(String text, float height, BaseColor colorborder,BaseColor color) {
        PdfPCell c1 = new PdfPCell(new Phrase(text));
        c1.setFixedHeight(height);
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
        c1.setBackgroundColor(color);
        c1.setBorderColor(colorborder);
        c1.setUseBorderPadding(true);
        return c1;
    }
    
    private PdfPTable TableIndex(int nbd ,int nbu) {
        
        PdfPTable t = new PdfPTable(4);
        
        t.setWidthPercentage(100);
        t.getDefaultCell().setPadding(5);
        t.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        
        t.addCell("IDENTIFIANT");
        t.addCell("NOM");
        t.addCell("PRENOM");
        t.addCell("SIGNATURE");
        
        t.setHeaderRows(1);

        for (int i = nbd; i < nbu ; i++) {
                t.addCell(rl.get(i).getUsername());
                t.addCell("");
                t.addCell("");
                t.addCell("");
            }
        
      return t;
        
    }
    
    public void HtmlToPDF( String evnt,String debut, String fin,String ssid,String infos,String gename) throws DocumentException, IOException {
            
   //   String contextPathOut = "/users/info/jlf/Documents/";
        
        String contextPathOut = "/home/jlfo/Documents/";
        float uservalh =75;
        
        PdfPTableEvent tableBackground;
        tableBackground = new TableBackground();
        
        Rectangle layout = new Rectangle(PageSize.A4);
        layout.setBackgroundColor(new BaseColor(100, 200, 180)); //Background color
        layout.setBorderColor(BaseColor.DARK_GRAY);  //Border color
        layout.setBorderWidth(6);      //Border width  
        layout.setBorder(Rectangle.BOX);  //Border on 4 sides
              
        Document document = new Document();
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        FileOutputStream os = new FileOutputStream(contextPathOut + gename + ".pdf");
        
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        writer.setInitialLeading(10);
        writer.setBoxSize("art", new Rectangle(36, 54, 559, 788));
        
   //   Image im = Image.getInstance(ContextPath + "protected/ecm_logo.png");
   //   Paragraph T = new Paragraph("ECOLE CENTRALE MARSEILLE", bigFont);
    
        Paragraph T1 = new Paragraph("Arruba Guest Provisioning", smallBold);
        
        TableHeader event = new TableHeader();
        writer.setPageEvent(event);
        
        document.open();
        
   //     T.setAlignment(Element.ALIGN_CENTER);
   //     T.setSpacingBefore(10);
   //     T.setSpacingAfter(20);
        
   //     im.scalePercent(50f);
   //     document.add(im);
   //     document.add(T);
   
    if ( !evnt.equals("Provisioning") && !evnt.equals("Guest")) {
        
        int reste = rl.size() % 25;
        int divent = rl.size()/25;
        
        if (rl.size() < 25) {
            PdfPTable ptm = TableIndex(0, rl.size());
            
            Paragraph in = new Paragraph("(Feuille d'émargement)",littleFont);            
            in.setAlignment(Element.ALIGN_CENTER);
            document.add(in);
            document.add(Chunk.NEWLINE);
            
            document.add(new Phrase("Événement : " + evnt.toUpperCase(),littleFont));
            document.add(Chunk.NEWLINE);
            document.add(new Phrase("Période du " + debut + " au " + fin,littleFont));
            
            document.add(ptm);
            document.newPage();
        } else {

            if (reste != 0) {
                for (int j = 0; j < divent; j++) {
                    
                    Paragraph in = new Paragraph("(Feuille d'émargement)", littleFont);
                    in.setAlignment(Element.ALIGN_CENTER);
                    document.add(in);
                    document.add(Chunk.NEWLINE);
                    
                    PdfPTable pt = TableIndex((j * 25), 25 + (j * 25));
                    document.add(new Phrase("Événement : " + evnt.toUpperCase(),littleFont));
                    document.add(Chunk.NEWLINE);
                    document.add(new Phrase("Période du " + debut + " au " + fin,littleFont));
                    document.add(pt);
                    document.newPage();
                }
                
                PdfPTable pti = TableIndex((divent * 25), rl.size());
                
                Paragraph in = new Paragraph("(Feuille d'émargement)", littleFont);
                in.setAlignment(Element.ALIGN_CENTER);
                document.add(in);
                document.add(Chunk.NEWLINE);
                
                document.add(new Phrase("Événement : " + evnt.toUpperCase(),littleFont));
                document.add(Chunk.NEWLINE);
                document.add(new Phrase("Période du " + debut + " au " + fin,littleFont));
                document.add(pti);
                document.newPage();
            } else {
                for (int j = 0; j < divent; j++) {
                    PdfPTable ptx = TableIndex((j * 25), 25 + (j * 25));
                    
                     Paragraph in = new Paragraph("(Feuille d'émargement)", littleFont);
                    in.setAlignment(Element.ALIGN_CENTER);
                    document.add(in);
                    document.add(Chunk.NEWLINE);
                    
                    document.add(new Phrase("Événement : " + evnt.toUpperCase(),littleFont));
                    document.add(Chunk.NEWLINE);
                    document.add(new Phrase("Période du " + debut + " au " + fin,littleFont));
                    document.add(ptx);
                    document.newPage();
                }
            }
        }
     }   
    /*  document.add(new Phrase("Evénement : " + evnt.toUpperCase()));       
        document.add(Chunk.NEWLINE);
        document.add(new Phrase("Du " + debut + " au " + fin));
    */      
        
               
        for ( int i=0;i<rl.size();i++) {
        
        if (!evnt.equals("Provisioning") && !evnt.equals("Guest")) {
        
        Paragraph PP =  new Paragraph("Événement : " + evnt.toUpperCase(),littleFont);
        PP.setSpacingBefore(40f);
        document.add(PP);       
        }
        
    //   Paragraph P =  new Paragraph("Informations de connexion" ,smallBold);
    //   P.setSpacingBefore(30f);
    //   document.add(P);
        
    //    PdfPTable contable = new PdfPTable(1);
    //    contable.setWidthPercentage(100);
    //    contable.setSpacingBefore(20f);
    //    contable.setSpacingAfter(10f);
        
        Paragraph P =  new Paragraph("Bonjour,\n\n");
        P.add(new Chunk("Voici votre identifiant et mot de passe pour accéder au réseau wifi de l'École Centrale Marseille.\n\n"));
        P.setSpacingBefore(10f);
        document.add(P);
        
        Paragraph parconx = new Paragraph();
     // parconx.setIndentationLeft(0);
        parconx.setAlignment(Element.ALIGN_CENTER); 
        parconx.setIndentationLeft(10);
       
        parconx.add(new Chunk(" - Vérifiez que votre adaptateur réseau est configuré en mode DHCP.\n "));
        parconx.add(new Chunk(" - Une fois connecté au réseau WIFI (indiqué ci -dessous), lancez votre navigateur.\n "));
        parconx.add(new Chunk(" - Vous serez alors redirigé vers le portail captif du réseau WIFI.\n "));
        parconx.add(new Chunk(" - Entrez le nom d'utilsateur et le mot de passe"));
        
    //    PdfPCell ConxcompositeCell = new PdfPCell();
    //    ConxcompositeCell.setFixedHeight(100);  

    //    ConxcompositeCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    //    ConxcompositeCell.setHorizontalAlignment(Element.ALIGN_CENTER);
    //    ConxcompositeCell.addElement(parconx);
    //  ConxcompositeCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
    //  ConxcompositeCell.setBorderColor(BaseColor.RED);
 
    //    contable.addCell(ConxcompositeCell);
        
    //  document.add(contable);
        document.add(parconx);
              
        Paragraph T0 =  new Paragraph("Informations Générales" ,smallBold);
        T0.setSpacingBefore(20f);
        document.add(T0);
        
        PdfPTable infotable = new PdfPTable(1);
        infotable.setWidthPercentage(100);
        infotable.setSpacingBefore(10f);
        infotable.setSpacingAfter(30f);
        
        Paragraph parinfo = new Paragraph();
        parinfo.setIndentationLeft(25);
        parinfo.setAlignment(Element.ALIGN_JUSTIFIED);
       
        if ( !evnt.equals("Provisioning") && !evnt.equals("Guest")) {
        parinfo.add(new Chunk("Lieu(x) de l'événement : ",smallBold));
        parinfo.add(new Chunk(infos));
        parinfo.add(Chunk.NEWLINE);
        } else {
        parinfo.add(new Chunk("Responsable Invités : ",smallBold));
        parinfo.add(new Chunk(infos));
        parinfo.add(Chunk.NEWLINE);
        }
        
        parinfo.add(new Chunk("Réseau WIFI de connexion : ",smallBold));
        parinfo.add(new Chunk(ssid.toUpperCase()));
        parinfo.add(Chunk.NEWLINE);
       
     /*   parinfo.add(new Chunk("Actif à partir du : " + debut));
        parinfo.add(Chunk.NEWLINE);
        
        parinfo.add(new Chunk("Expiration le : " + fin));
        parinfo.add(Chunk.NEWLINE);
     */
        
        PdfPCell InfocompositeCell = new PdfPCell();
        InfocompositeCell.setFixedHeight(50);  

        InfocompositeCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        InfocompositeCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        InfocompositeCell.addElement(parinfo);
    //  InfocompositeCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        InfocompositeCell.setBorderColor(BaseColor.DARK_GRAY);
 
        infotable.addCell(InfocompositeCell);
        
        document.add(infotable);
            
        PdfPTable table = new PdfPTable(1);
     //   table.setSpacingBefore(10f);
     //   table.setSpacingAfter(10f);  
        float widthval=75;
        table.setWidthPercentage(100);
        
        PdfPTable usertable = new PdfPTable(1);
    //  usertable.setSpacingBefore(10f);
    //  usertable.setSpacingAfter(10f);  
        
        usertable.setWidthPercentage(100);    
        
    //   im.scalePercent(50f);
    //   document.add(im);
                  
  //     document.add(T);
  //     document.add(Chunk.NEWLINE);
        
        Paragraph T2 =  new Paragraph("Informations Utilisateur" ,smallBold);
        T2.setSpacingAfter(10f);
        document.add(T2);
       
        Paragraph preface = new Paragraph();
       
        preface.setIndentationLeft(25);
        
        preface.add(new Chunk("WI-FI Username : ",smallBold));
        preface.add(new Chunk(rl.get(i).getUsername()));
        preface.add(Chunk.NEWLINE);
        
        preface.add(new Chunk("WI-FI Password : ",smallBold));
        preface.add(new Chunk(rl.get(i).getValue()));
        preface.add(Chunk.NEWLINE);
        
        if ( !evnt.equals("Provisioning") && !evnt.equals("Guest")) {
        preface.add(new Chunk("Actif à partir du : ",smallBold));
        preface.add(new Chunk(debut));
        preface.add(Chunk.NEWLINE);
        
        preface.add(new Chunk("Expiration le : ",smallBold));
        preface.add(new Chunk(fin));
        preface.add(Chunk.NEWLINE);
        } else {
        preface.add(new Chunk("Compte valide ",smallBold));
        preface.add(new Chunk(fin));
        preface.add(new Chunk(" à partir de la première connexion",smallBold));
        preface.add(Chunk.NEWLINE);
        }
        
        PdfPCell compositeCell = new PdfPCell();
        compositeCell.setFixedHeight(100);
        compositeCell.addElement(preface);
        compositeCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        compositeCell.setHorizontalAlignment(Element.ALIGN_CENTER);
       
  //    compositeCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        compositeCell.setBorderColor(BaseColor.LIGHT_GRAY);
        
        usertable.addCell(compositeCell);
        
        document.add(usertable);
        
        Paragraph parcon = new Paragraph("Conditions d'usage :\n\n",smallBold);
        parcon.setFont(italicfonts);
        parcon.add("Ce compte est destiné à un usage strictement personnel. Vous êtes responsable de la confidentialité de vos"
                + " identifiants et de toutes les activités réalisées sous cette identité.\n"
                + "Remarque importante : si vous avez un compte eduroam utilisez en priorité celui-ci");
        parcon.setSpacingBefore(40f);
        document.add(parcon);
        
   //   document.add(Chunk.NEWLINE);
 
  /*    PdfPCell defaultCell = table.getDefaultCell();
        
        PdfPCell c1 = Create_Cell("Table Header", 25, BaseColor.BLACK,BaseColor.CYAN);
        
        table.addCell(c1);
      
        defaultCell = Create_Cell("Cell 5 Center align contents of the Cell using iText API", widthval, BaseColor.BLACK,BaseColor.GRAY);
        table.addCell(defaultCell);  
        
        PdfPCell onecell = Create_Cell("SALUT LOULOU CA VA", 25, BaseColor.RED,BaseColor.ORANGE);
        table.addCell(onecell);
        
     //   document.add(Chunk.NEWLINE);
        table.addCell(defaultCell);
        
        PdfPCell compositeModeCell = new PdfPCell();
        compositeModeCell.setFixedHeight(50);
        
        Paragraph pp = new Paragraph("Composite Mode CELL",redFont);
        pp.setAlignment(Element.ALIGN_CENTER);
        
        compositeModeCell.addElement(pp);
        compositeModeCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        compositeModeCell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
        compositeModeCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(compositeModeCell);
        
        document.add(table);
*/
  //      XMLWorkerHelper.getInstance().parseXHtml(writer, document,
  //              new FileInputStream(ContextPath + "htmltext.html"), new FileInputStream(ContextPath + "style.css") ); 
        
        if ( i < ( rl.size() -1) ) {
            document.newPage();
        }
        
        }
        
        addMetaData(document);
        document.close();
 
        baos.writeTo(os);
        
        System.out.println( "PDF Created!" );
           
   /*      
        XMLWorkerHelper helper = XMLWorkerHelper.getInstance();
        
        // CSS
        CSSResolver cssResolver = new StyleAttrCSSResolver();
        CssFile cssFile = helper.getCSS(new FileInputStream(contextPath + "style.css"));
        cssResolver.addCss(cssFile);
        
        XMLWorkerFontProvider fontProvider = new XMLWorkerFontProvider();
         
        CssAppliers cssAppliers = new CssAppliersImpl(fontProvider);
        HtmlPipelineContext htmlContext = new HtmlPipelineContext(cssAppliers);
        
        htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());
        
         // Pipelines
        PdfWriterPipeline pdf = new PdfWriterPipeline(document, writer);
        HtmlPipeline html = new HtmlPipeline(htmlContext, pdf);
        CssResolverPipeline css = new CssResolverPipeline(cssResolver, html);
        
        XMLWorker worker = new XMLWorker(css, true);
        XMLParser p = new XMLParser(worker);
      
        p.parse(new FileInputStream(contextPath + "htmltext.html"));
     */
        
    } 
}
