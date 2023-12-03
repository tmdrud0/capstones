package capstone.app.repository;

import capstone.app.domain.Deal;
import capstone.app.domain.Measurement;
import capstone.app.exception.CustomException;
import capstone.app.exception.ErrorCode;
import capstone.app.service.PdfService;
import capstone.app.service.UserService;
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
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Repository;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Repository
@RequiredArgsConstructor
public class PdfRepository {

    private final UserService userService;
    public FileSystemResource find(String path) {
        try {
            FileSystemResource resource = new FileSystemResource(path);
            if (!resource.exists()) {
                throw new CustomException(ErrorCode.PDF_NOT_FOUND);
            }
            return resource;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.PDF_READ_FAIL);
        }
    }

    private String getPath(){
        return "resources/pdfs/" + userService.getMyUserWithAuthorities().get().getUsername()+"/";
    }
    public void generatePdf(Deal deal){
        try {

            PdfWriter writer = new PdfWriter(getPath() + deal.getPdf());
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.setMargins(50,50,50,50);
            PdfFont font = PdfFontFactory.createFont("NanumGothic.ttf", PdfEncodings.IDENTITY_H, true);
            document.setFont(font);

            DealGenerator dealGenerator = new DealGenerator(document);
            dealGenerator.generate(deal);

            MeasurementGenerator measurementGenerator = new MeasurementGenerator(document);
            for(int i = 0; i < deal.getMeasurements().size();i++)
            {
                document.add(new AreaBreak());
                measurementGenerator.generate(deal.getMeasurements().get(i),userService.getMyUserWithAuthorities().get().getUsername());
            }

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

        private final String CAR_FRONT = "carLnumFront.jpg";
        private final String CAR_BACK = "carLnumBack.jpg";
        private final String CAR_FIRST = "picCar1st.jpg";
        private final String CAR_SECOND = "picCar2nd.jpg";

        private String getPath(Measurement measurement, String username){
            return "resources/images/" + username + "/" + measurement.getId() + "/";
        }
        public Document generate(Measurement measurement, String username) throws IOException {
            String path = getPath(measurement, username);

            Table table = new Table(new float[]{2, 2, 2});
            File frontSource = new File(path + CAR_FRONT);
            File backSource = new File(path + CAR_BACK);
            File firstSource = new File(path + CAR_FIRST);
            File secondSource = new File(path + CAR_SECOND);

            BufferedImage frontBufferedImage = ImageIO.read(frontSource);
            BufferedImage backBufferedImage = ImageIO.read(backSource);
            BufferedImage firstBufferedImage = ImageIO.read(firstSource);
            BufferedImage secondBufferedImage = ImageIO.read(secondSource);

            // BufferedImage를 ImageData로 변환
            ImageData frontImageData = ImageDataFactory.create(frontBufferedImage, null);
            ImageData backImageData = ImageDataFactory.create(backBufferedImage, null);
            ImageData firstImageData = ImageDataFactory.create(firstBufferedImage, null);
            ImageData secondImageData = ImageDataFactory.create(secondBufferedImage, null);

            // ImageData로부터 iText의 Image 객체를 생성
            Image frontItextImage = new Image(frontImageData);
            Image backItextImage = new Image(backImageData);
            Image firstItextImage = new Image(firstImageData);
            Image secondItextImage = new Image(secondImageData);

            frontItextImage.scaleAbsolute(247,150);
            backItextImage.scaleAbsolute(247,150);
            firstItextImage.scaleAbsolute(247,150);
            secondItextImage.scaleAbsolute(247,150);
            table.addCell(new Cell().add(new Paragraph(" 1\n차\n계\n량\n영\n상")));
            table.addCell(new Cell().add(frontItextImage));
            table.addCell(new Cell().add(firstItextImage));

            table.addCell(new Cell().add(new Paragraph("2\n차\n계\n량\n영\n상")));
            table.addCell(new Cell().add(backItextImage));
            table.addCell(new Cell().add(secondItextImage));

            // 표의 스타일과 위치를 설정합니다.
            table.setWidth(500);
            table.setHorizontalAlignment(HorizontalAlignment.CENTER);

            Table table2 = new Table(new float[]{2, 2, 2, 2});

            table2.addCell(new Cell().add(new Paragraph("계량일자").setWidth(80)));
            table2.addCell(new Cell().add(new Paragraph(measurement.getDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG))).setWidth(170)));
            table2.addCell(new Cell().add(new Paragraph("총중량").setWidth(80)));
            table2.addCell(new Cell().add(new Paragraph(String.valueOf(measurement.getTotalWeight())).setWidth(170)));

            table2.addCell(new Cell().add(new Paragraph("차량번호").setWidth(80)));
            table2.addCell(new Cell().add(new Paragraph(measurement.getLiscenseNum()).setWidth(170)));
            table2.addCell(new Cell().add(new Paragraph("공차중량").setWidth(80)));
            table2.addCell(new Cell().add(new Paragraph(String.valueOf(measurement.getEmptyWeight())).setWidth(170)));

            table2.addCell(new Cell().add(new Paragraph("거래처").setWidth(80)));
            table2.addCell(new Cell().add(new Paragraph(measurement.getClient()).setWidth(170)));
            table2.addCell(new Cell().add(new Paragraph("실중량").setWidth(80)));
            table2.addCell(new Cell().add(new Paragraph(String.valueOf(measurement.getActualWeight())).setWidth(170)));

            table2.addCell(new Cell().add(new Paragraph("품목").setWidth(80)));
            table2.addCell(new Cell().add(new Paragraph(measurement.getItem()).setWidth(170)));
            table2.addCell(new Cell().add(new Paragraph("감율/감량").setWidth(80)));
            table2.addCell(new Cell().add(new Paragraph("0").setWidth(170)));

            table2.addCell(new Cell().add(new Paragraph("단가").setWidth(80)));
            table2.addCell(new Cell().add(new Paragraph(String.valueOf(measurement.getUnitCost())).setWidth(170)));
            table2.addCell(new Cell().add(new Paragraph("인수량").setWidth(80)));
            table2.addCell(new Cell().add(new Paragraph(String.valueOf(measurement.getActualWeight())).setWidth(170)));

            table2.addCell(new Cell().add(new Paragraph("비고").setWidth(80).setHeight(30)));
            table2.addCell(new Cell(1,3).add(new Paragraph(measurement.getNote())));

            table2.addCell(new Cell().add(new Paragraph("운반자").setWidth(80)));
            table2.addCell(new Cell().add(new Paragraph(measurement.getDriverName()).setWidth(170)));
            table2.addCell(new Cell().add(new Paragraph("전화번호").setWidth(80)));
            table2.addCell(new Cell().add(new Paragraph(measurement.getDriverTP()).setWidth(170)));

            table2.addCell(new Cell(1,4).add(new Paragraph("주식회사\n").setFontSize(20))
                    .add(new Paragraph("e-Mail : " + measurement.getUser().getCompany().getCompanyEmail() + "/n"))
                    .add(new Paragraph("경기도 화성시 서봉로 1123-123\n"))
                    .add(new Paragraph("TEL : " + measurement.getUser().getCompany().getCompanyCallNumber() +  " / FAX : " + measurement.getUser().getCompany().getCompanyFaxNumber() +  "\n"))
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
        public Document generate(Deal deal) throws IOException {

            // 거래내역을 담을 표를 생성합니다.
            Table table = new Table(new float[]{2, 2, 2, 2, 2, 2}); // 6개의 열로 구성된 표

            // 표의 열 이름을 추가합니다.
            table.addCell(new Cell().add(new Paragraph("년 월 일")));
            table.addCell(new Cell().add(new Paragraph("차량번호")));
            table.addCell(new Cell().add(new Paragraph("총중량")));
            table.addCell(new Cell().add(new Paragraph("공차중량")));
            table.addCell(new Cell().add(new Paragraph("감량")));
            table.addCell(new Cell().add(new Paragraph("실중량")));

            deal.getCarDto().forEach(c -> {
                table.addCell(new Cell().add(new Paragraph(c.getDate().format(DateTimeFormatter.BASIC_ISO_DATE))));
                table.addCell(new Cell().add(new Paragraph(c.getLicenseNum())));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(c.getTotalWeight()))));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(c.getEmptyWeight()))));
                table.addCell(new Cell().add(new Paragraph("0")));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(c.getActualWeight()))));
            });

            for(int i=0;i<3-deal.getCarDto().size();i++)
                for(int j=0;j<6;j++)    table.addCell("\n");

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

            deal.getMeasurementDto().forEach(m -> {
                table2.addCell(new Cell().add(new Paragraph(m.getName())));
                table2.addCell(new Cell().add(new Paragraph(String.valueOf(m.getWeight()))));
                table2.addCell(new Cell().add(new Paragraph(String.valueOf(m.getUnitPrice()))));
                table2.addCell(new Cell().add(new Paragraph(String.valueOf(m.getTotalPrice()))));
                table2.addCell(new Cell().add(new Paragraph("0")));
                table2.addCell(new Cell().add(new Paragraph(m.getFirstTime().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)))));
                table2.addCell(new Cell().add(new Paragraph(m.getEndTime().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)))));
            });

            for(int i=0;i<9-deal.getMeasurementDto().size();i++)
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
                    .add(deal.getMeasurements().get(0).getClient())
                    .setTextAlignment(TextAlignment.CENTER)
                    .setPaddingTop(50);

            Paragraph companyInfo = new Paragraph()
                    .add("상호) " + deal.getCompany().getCompanyName() +  "\n")
                    .add("주소) " + deal.getCompany().getCompanyAddress() +  "\n")
                    .add("전화) " + deal.getCompany().getCompanyCallNumber() +  "\n")
                    .add("팩스) " + deal.getCompany().getCompanyFaxNumber() )
                    .setTextAlignment(TextAlignment.LEFT)
                    .setMarginBottom(30);

            Paragraph title = new Paragraph("거래명세서")
                    .setFontSize(24) // 제목 크기를 키웁니다.
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFixedLeading(100);

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
