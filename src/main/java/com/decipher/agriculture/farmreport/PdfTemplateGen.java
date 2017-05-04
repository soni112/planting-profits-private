package com.decipher.agriculture.farmreport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.itextpdf.text.Font;

@Component
public class PdfTemplateGen {

    private static final Logger logger = LoggerFactory.getLogger(PdfTemplateGen.class);

    public static final Font[] FONT = new Font[6];

    static {

        FONT[0] = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
        FONT[1] = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
        FONT[2] = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
        FONT[3] = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
        FONT[4] = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
        FONT[5] = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);

    }

    public final static int[] EMI_COL_WIDTH = new int[]{50, 50, 70, 50, 50, 50, 50, 70, 50};
    public final static int[] EMI_COL_WIDTH_3 = new int[]{200, 200, 200};
    //public final static Chunk TAB=new Chunk(new VerticalPositionMark(), 150, true);
    public final static int PADDING = 10;
    public final static int DEF_PADDING = 4;


	/*public void finalITCertificatePdf(Document document, ITCertificateBean itCertificateBean, Map value) throws DocumentException, MalformedURLException, IOException {

		document.setPageSize(new Rectangle(612f, 1008));
		document.addAuthor("Etrupti");
		document.addCreator("Etrupti-WebApp");
		document.setMargins(35f, 35f, 35f, 110f);
		Font hading = new Font(Font.TIMES_ROMAN, 9, Font.BOLD);
		Font simple = new Font(Font.TIMES_ROMAN, 9, Font.NORMAL);
		Font ad = new Font(Font.TIMES_ROMAN, 10, Font.BOLD);
		document.open();
		Paragraph TopHedding = new Paragraph("FINAL STATEMENT FOR CLAIMING DEDUCTIONS UNDER SECTIONS \n 80C & 24(b) OF THE INCOME TAX ACT, 1961 \n\n", hading);
		TopHedding.setAlignment(Element.ALIGN_CENTER);
		TopHedding.setSpacingBefore(0);
		TopHedding.setLeading(12);
		TopHedding.setIndentationLeft(35);
		document.add(TopHedding);

		Paragraph aob = new Paragraph("Address of Borrower - \n", hading);
		aob.setSpacingAfter(9);
		document.add(aob);

		Paragraph address = new Paragraph();
		// logger.info("itCertificateBean.getAddress2()="+itCertificateBean.getAddress2());
		// logger.info("itCertificateBean.getCity()="+itCertificateBean.getCity());
		// logger.info("itCertificateBean.getZipcode()="+itCertificateBean.getZipcode());
		Chunk add = new Chunk(itCertificateBean.getCustomerName() + " \n " + (itCertificateBean.getAddress1() != null ? (itCertificateBean.getAddress1() + ",\n ") : "")
				+ (itCertificateBean.getAddress2() != null ? (itCertificateBean.getAddress2() + ",\n ") : "") + (itCertificateBean.getAddress3() != null ? (itCertificateBean.getAddress3() + ", \n") : "")
				+ (itCertificateBean.getAddress4() != null ? (itCertificateBean.getAddress4() + ", \n") : "") + itCertificateBean.getCity() + "-" + itCertificateBean.getZipcode() + "\n Contact No. - "
				+ (itCertificateBean.getPhoneNo() != null ? itCertificateBean.getMobileNo() : itCertificateBean.getMobileNo()));
		address.setAlignment(0);
		address.setFont(ad);
		address.setLeading(13);
		address.setSpacingAfter(0);
		address.setSpacingBefore(0);
		address.setIndentationLeft(2);
		address.add(add);
		document.add(address);

		Paragraph concern = new Paragraph("\nTo Whomsoever It May Concern \n", hading);
		concern.setAlignment(Element.ALIGN_CENTER);
		concern.setLeading(15);
		concern.setSpacingAfter(5);
		document.add(concern);

		Chunk name = new Chunk(itCertificateBean.getCustomerName(), hading);
		Chunk dec = new Chunk("This is to state that ", simple);
		Chunk dec1 = new Chunk(" with loan account No. " + itCertificateBean.getAgreementNo() + " has / have been granted loan for House Property of ", simple);
		Chunk dec2 = new Chunk("Rs." + itCertificateBean.getDisbursedAmt(), hading);
		Chunk dec3 = new Chunk(" @ " + itCertificateBean.getEffRate() + " % p.a. in respect of the following property:", simple);
		Paragraph to = new Paragraph();
		to.add(dec);
		to.add(name);
		to.add(dec1);
		to.add(dec2);
		to.add(dec3);
		to.setLeading(11);
		to.setAlignment(Element.ALIGN_JUSTIFIED);
		document.add(to);
		Paragraph add1 = new Paragraph("\n " + itCertificateBean.getPropertyDesc() + " \n " + itCertificateBean.getProAddress1() + " \n " + itCertificateBean.getProAddress2() + ", \n " + itCertificateBean.getProAddress3() + ", \n "
				+ itCertificateBean.getProCity() + "-" + itCertificateBean.getProZipCode(), simple);
		add1.setAlignment(Element.ALIGN_JUSTIFIED);
		add1.setLeading(10);
		add1.setSpacingAfter(0);
		document.add(add1);

		Paragraph amt = new Paragraph("\nThe interest amount received and principal component repaid during the period " + itCertificateBean.getFromDate() + " to " + itCertificateBean.getToDate()
				+ " with respect to the above loan is as given below:\n \n", simple);
		amt.setAlignment(Element.ALIGN_JUSTIFIED);
		amt.setLeading(10);
		amt.setSpacingAfter(0);
		document.add(amt);

		PdfPTable tbl = new PdfPTable(2);
		tbl.setWidthPercentage(100);
		Phrase ps1 = new Phrase();
		ps1.add(" Interest Component \n GAP/Broken Period Interest");
		ps1.add("\n Total EMI Amount \n Principal Component");
		Phrase ps2 = new Phrase();
		ps2.add("\n" + itCertificateBean.getIntEmiAmt() + " \n");
		ps2.add(itCertificateBean.getGapInterestAmt() + " \n");
		ps2.add(itCertificateBean.getTotalEmiAmt() + " \n");
		ps2.add(itCertificateBean.getPtinEmiAmt());
		ps2.setLeading(100);
		Paragraph p1 = new Paragraph();
		p1.setFont(hading);
		p1.setLeading(20);
		p1.add(ps1);
		Paragraph p2 = new Paragraph("Paid from " + itCertificateBean.getFromDate() + " to " + itCertificateBean.getToDate() + " \n", hading);
		p2.setLeading(20);
		p2.add(ps2);
		PdfPCell c1 = new PdfPCell(p1);
		PdfPCell c2 = new PdfPCell(p2);
		c2.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setPaddingTop(20);
		c2.setPaddingTop(5);
		c1.setPaddingLeft(50);
		c1.setPaddingBottom(10);
		tbl.addCell(c1);
		tbl.addCell(c2);
		document.add(tbl);

		Phrase ph = new Phrase(" \nThe Pre-EMI Interest received from " + itCertificateBean.getFromDate() + " to " + itCertificateBean.getToDate() + " is Rs. " + itCertificateBean.getPemiAmt(), simple);
		document.add(ph);
		Phrase ph1 = new Phrase("\n Please Note:", hading);
		document.add(ph1);
		Paragraph note = new Paragraph(10);
		note.setFont(simple);
		Phrase l1 = new Phrase("1) Principal Repayments through EMI qualify for deduction if the amounts are actually paid by 31-MAR-15.");
		l1.add("\n 2) The onus of establishing the eligibility for claiming deductions under Income Tax vests with the subject client and these have not been\n     verified/vouched by the company.");
		l1.add("\n 3) This certification is issued to enable the borrower to claim the applicable tax benefits. However, deduction under section  24(b) in respect of\n      interest payable on capital borrowed for acquisition/construction of the property and further deduction");
		l1.add("\n     under section 80C of the Act in respect of repayment of principal borrowed for acquisition/construction of a residential house property can be \n     claimed subject to the fulfillment of conditions prescribed under these sections");
		note.add(l1);
		document.add(note);
		Phrase ph2 = new Phrase("\n" + value.get("FOR") + ":\n\n", hading);
		document.add(ph2);
		Image image1 = Image.getInstance(Thread.currentThread().getContextClassLoader().getResource(value.get("SIGN_IMAGE").toString()));
		image1.scaleAbsolute(120, 40);
		document.add(image1);
		Phrase sign = new Phrase(" " + value.get("SIGN") + " \n", hading);
		document.add(sign);
		Phrase deg = new Phrase("" + value.get("DESIGNATION") + " \n\n\n", hading);
		document.add(deg);
		Phrase c_care = new Phrase("\nCustomer Care No : " + value.get("CUSTOMER_CARE_NO") + " (Toll Free) \n Email Id : " + value.get("EMAIL_ID") + "\n Website : " + value.get("WEBSITE") + " \n ", simple);
		c_care.add("\nCall us on : " + value.get("CALL_US_ON") + " \n\nCIN : " + value.get("CIN") + "");
		c_care.setLeading(10);
		document.add(c_care);

		Phrase endDec = new Phrase(" \nThis is a computer generated statement and does not require signature.\n Please Note:-", simple);
		endDec.add("\nAs per the Income Tax Act, 1961, if you are required to deposit TDS on the Interest component of the Installment, we request you to submit the TDS ");
		endDec.add(" certificate at the branch nearest to you. We shall arrange for the amount in the TDS certificate to be sent to you on your mailing address within 11 working");
		endDec.add("days of submitting the TDS certificate. The PAN No of RELIANCE CAPITAL LIMITED is AAACR5054J and Service Tax No is AAACR5054JST001.");
		endDec.add("The Registered Address of the Company is RELIANCE CAPITAL LIMITED, H-Block, 1st Floor, Dhirubhai Ambani Knowledge City, Navi Mumbai - 400710.");
		endDec.setLeading(10);
		document.add(endDec);

	}*/
}
