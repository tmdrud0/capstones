package capstone.app.service;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Component
public class PdfService {

    public void generate(){
        try {
            String filePath = "t.pdf";
            PdfWriter writer = new PdfWriter(filePath);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.setMargins(50,50,50,50);
            PdfFont font = PdfFontFactory.createFont("NanumGothic.ttf", PdfEncodings.IDENTITY_H, true);
            document.setFont(font);

            DealGenerator dealGenerator = new DealGenerator(document);
            dealGenerator.generate();
            document.add(new AreaBreak());

            MeasurementGenerator measurementGenerator = new MeasurementGenerator(document);
            measurementGenerator.generate();

            document.close();
        }   catch (IOException e) {
            e.printStackTrace();
        }
    }




    static class MeasurementGenerator{
        Document document;

        public MeasurementGenerator(Document document) {
            this.document = document;
        }

        public Document generate() throws IOException {

            Table table = new Table(new float[]{2, 2, 2});
            File sourceimage = new File("t.png");
            BufferedImage bufferedImage = ImageIO.read(sourceimage);

            // BufferedImage를 ImageData로 변환
            ImageData imageData = ImageDataFactory.create(bufferedImage, null);

            // ImageData로부터 iText의 Image 객체를 생성
            Image itextImage = new Image(imageData);

            itextImage.scaleAbsolute(247,150);
            table.addCell(new Cell().add(new Paragraph(" 1\n차\n계\n량\n영\n상")));
            table.addCell(new Cell().add(itextImage));
            table.addCell(new Cell().add(itextImage));

            table.addCell(new Cell().add(new Paragraph("2\n차\n계\n량\n영\n상")));
            table.addCell(new Cell().add(itextImage));
            table.addCell(new Cell().add(itextImage));

            // 표의 스타일과 위치를 설정합니다.
            table.setWidth(500);
            table.setHorizontalAlignment(HorizontalAlignment.CENTER);

            Table table2 = new Table(new float[]{2, 2, 2, 2});

            table2.addCell(new Cell().add(new Paragraph("계량일자").setWidth(80)));
            table2.addCell(new Cell().add(new Paragraph(" ").setWidth(170)));
            table2.addCell(new Cell().add(new Paragraph("총중량").setWidth(80)));
            table2.addCell(new Cell().add(new Paragraph("").setWidth(170)));

            table2.addCell(new Cell().add(new Paragraph("차량번호").setWidth(80)));
            table2.addCell(new Cell().add(new Paragraph(" ").setWidth(170)));
            table2.addCell(new Cell().add(new Paragraph("공차중량").setWidth(80)));
            table2.addCell(new Cell().add(new Paragraph("").setWidth(170)));

            table2.addCell(new Cell().add(new Paragraph("거래처").setWidth(80)));
            table2.addCell(new Cell().add(new Paragraph(" ").setWidth(170)));
            table2.addCell(new Cell().add(new Paragraph("실중량").setWidth(80)));
            table2.addCell(new Cell().add(new Paragraph("").setWidth(170)));

            table2.addCell(new Cell().add(new Paragraph("품목").setWidth(80)));
            table2.addCell(new Cell().add(new Paragraph(" ").setWidth(170)));
            table2.addCell(new Cell().add(new Paragraph("감율/감량").setWidth(80)));
            table2.addCell(new Cell().add(new Paragraph("").setWidth(170)));

            table2.addCell(new Cell().add(new Paragraph("단가").setWidth(80)));
            table2.addCell(new Cell().add(new Paragraph(" ").setWidth(170)));
            table2.addCell(new Cell().add(new Paragraph("인수량").setWidth(80)));
            table2.addCell(new Cell().add(new Paragraph("").setWidth(170)));

            table2.addCell(new Cell().add(new Paragraph("비고").setWidth(80).setHeight(30)));
            table2.addCell(new Cell(1,3));

            table2.addCell(new Cell().add(new Paragraph("운반자").setWidth(80)));
            table2.addCell(new Cell().add(new Paragraph(" ").setWidth(170)));
            table2.addCell(new Cell().add(new Paragraph("전화번호").setWidth(80)));
            table2.addCell(new Cell().add(new Paragraph("").setWidth(170)));

            table2.addCell(new Cell(1,4).add(new Paragraph("주식회사\n").setFontSize(20))
                    .add(new Paragraph("e-Mail : \n"))
                    .add(new Paragraph("경기도 화성시 서봉로 1123-123\n"))
                    .add(new Paragraph("TEL : 010-2323-3124 / FAX : 003-1314-1234\n"))
                    .add(new Paragraph("* 위와 같이 계량하였음을 확인함.").setTextAlignment(TextAlignment.LEFT).setFontSize(10)));


            table2.setWidth(500);
            table2.setHorizontalAlignment(HorizontalAlignment.CENTER);
            table2.setTextAlignment(TextAlignment.CENTER);
            table2.setVerticalAlignment(VerticalAlignment.MIDDLE);

            Table table3 = new Table(new float[1]);

            table3.addCell(new Cell().add(new Paragraph("확인자").setTextAlignment(TextAlignment.CENTER).setFontSize(8)));
            table3.addCell(new Cell().setHeight(40));
            table3.setFixedPosition(500, 110, 60);

            Paragraph title = new Paragraph("계량확인서")
                    .setFontSize(24) // 제목 크기를 키웁니다.
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFixedLeading(100);

            // 문서에 추가합니다.
            document.add(title);
            document.add(table);
            document.add(table2);
            document.add(table3);

            return document;
        }
    }


    static class DealGenerator{
        Document document;

        public DealGenerator(Document document) {
            this.document = document;
        }
        public Document generate() throws IOException {

            // 거래내역을 담을 표를 생성합니다.
            Table table = new Table(new float[]{2, 2, 2, 2, 2, 2}); // 6개의 열로 구성된 표

            // 표의 열 이름을 추가합니다.
            table.addCell(new Cell().add(new Paragraph("시간")));
            table.addCell(new Cell().add(new Paragraph("차량번호")));
            table.addCell(new Cell().add(new Paragraph("총중량")));
            table.addCell(new Cell().add(new Paragraph("공차중량")));
            table.addCell(new Cell().add(new Paragraph("감량")));
            table.addCell(new Cell().add(new Paragraph("실중량")));

            // 실제 거래내역을 추가합니다.
            table.addCell(new Cell().add(new Paragraph("12:30")));
            table.addCell(new Cell().add(new Paragraph("1234 가 5678")));
            table.addCell(new Cell().add(new Paragraph("500 kg")));
            table.addCell(new Cell().add(new Paragraph("10 kg")));
            table.addCell(new Cell().add(new Paragraph("5 kg")));
            table.addCell(new Cell().add(new Paragraph("485 kg")));
            for(int i=0;i<2;i++)
                for(int j=0;j<6;j++)    table.addCell("\n");
            // 빈 행 추가


            // 표의 스타일과 위치를 설정합니다.
            table.setTextAlignment(TextAlignment.CENTER);
            table.setFontSize(10);
            table.setWidth(500);
            table.setHorizontalAlignment(HorizontalAlignment.CENTER);

            Table table2 = new Table(new float[]{2, 1, 1, 2, 2, 2, 2});


            table2.addCell(new Cell().add(new Paragraph("품목")));
            table2.addCell(new Cell().add(new Paragraph("kg")));
            table2.addCell(new Cell().add(new Paragraph("단가")));
            table2.addCell(new Cell().add(new Paragraph("금액")));
            table2.addCell(new Cell().add(new Paragraph("감량내역")));
            table2.addCell(new Cell().add(new Paragraph("입차")));
            table2.addCell(new Cell().add(new Paragraph("출차")));

            table2.addCell(new Cell().add(new Paragraph("데이터1")));
            table2.addCell(new Cell().add(new Paragraph("데이터2")));
            table2.addCell(new Cell().add(new Paragraph("데이터3")));
            table2.addCell(new Cell().add(new Paragraph("데이터4")));
            table2.addCell(new Cell().add(new Paragraph("데이터5")));
            table2.addCell(new Cell().add(new Paragraph("데이터6")));
            table2.addCell(new Cell().add(new Paragraph("데이터7")));

            for(int i=0;i<9;i++)
                for(int j=0;j<7;j++)    table2.addCell("\n");

            table2.setTextAlignment(TextAlignment.CENTER);
            table.setFontSize(10);
            table2.setWidth(500);
            table2.setHorizontalAlignment(HorizontalAlignment.CENTER);



            Table table3 = new Table(new float[]{2, 2});
            table3.addCell(new Cell().add(new Paragraph("담당자")).setTextAlignment(TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE));
            table3.addCell(new Cell().setWidth(60));
            table3.setHeight(40);
            table3.setWidth(120);
            table3.setFixedPosition(435,630,120);

            Paragraph footCompany = new Paragraph()
                    .add("우주금속산업")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setPaddingTop(50);

            // 문서에 상호, 주소, 전화, 팩스 정보를 추가합니다.
            Paragraph companyInfo = new Paragraph()
                    .add("상호) 샘플 상호\n")
                    .add("주소) 샘플 주소\n")
                    .add("전화) 123-456-7890\n")
                    .add("팩스) 123-456-7890")
                    .setTextAlignment(TextAlignment.LEFT)
                    .setMarginBottom(30);

            // 거래명세서 제목을 추가합니다.
            Paragraph title = new Paragraph("거래명세서")
                    .setFontSize(24) // 제목 크기를 키웁니다.
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFixedLeading(100);

            // 문서에 추가합니다.
            document.add(title);
            document.add(companyInfo);
            document.add(table);
            document.add(table2);
            document.add(table3);
            document.add(footCompany);

            return document;
        }
    }
}