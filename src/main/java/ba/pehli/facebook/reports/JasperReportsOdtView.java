package ba.pehli.facebook.reports;

import net.sf.jasperreports.engine.export.oasis.JROdtExporter;

import org.springframework.web.servlet.view.jasperreports.AbstractJasperReportsSingleFormatView;

/**
 * Custom view za odt format, pogledati mvc-config.xml i konfiguraciju za JasperReportsMultiFormatView 
 * @author almir
 *
 */
public class JasperReportsOdtView extends AbstractJasperReportsSingleFormatView{
	public JasperReportsOdtView() {
		setContentType("application/msword");
	}

	@Override
	protected net.sf.jasperreports.engine.JRExporter createExporter() {
		return new JROdtExporter();
	}

	@Override
	protected boolean useWriter() {
		return false;
	}
}
