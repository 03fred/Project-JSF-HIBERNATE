package br.com.project.report.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;


import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.stereotype.Component;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.oasis.JROdtExporter;
import net.sf.jasperreports.engine.util.JRLoader;

@Component
public class ReportUtil implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String UNDERLINE = "_";
	private static final String FOLDER_RELATORIOS = "/relatorios";
	private static final String SUBREPORT_DIR = "SUBREPORT_DIR";
	private static final String EXTESION_ODS = "ods";
	private static final String EXTESION_XLS = "xls";
	private static final String EXTESION_HTML = "html";
	private static final String EXTESION_PDF = "pdf";
	private String SEPARATOR = File.separator;
	private static final int RELATORIO_PDF = 1;
	private static final int RELATORIO_EXCEL = 2;
	private static final int RELATORIO_HTML = 3;
	private static final int RELATORIO_PLANILHA_OPEN_0FFICE = 4;
	private static final String PONTO = ".";
	private StreamedContent arquivoRetorno = null;
	private String caminhoArquivoRelatorio = null;
	private JRExporter tipoArquivoExportado = null;
	private String ExtensaoArquivoExportado = "";
	private File arquivoGerado = null;
	private String caminhoSubreport_dir = "";

	/**
	 * FORNECE O CAMINHO FÍSICO ATÉ A PASTA DE CONTÉM OS RELATÓRIOS COMPILADOS .JASP
	 * 
	 * @param listDataBeanColletionReport
	 * @param parametrosRelatorio
	 * @param nomeRelatorioJasper
	 * @param nomeRelatorioSaida
	 * @param tipoRelatorio
	 * @return
	 * @throws Exception
	 */

	public StreamedContent geraRelatorio(List<?> listDataBeanColletionReport, HashMap parametrosRelatorio,
			String nomeRelatorioJasper, String nomeRelatorioSaida, int tipoRelatorio) throws Exception {

		/*
		 * cria a lista de collectionDataSource de beans que carregam os dados para o
		 * relatório
		 */

		JRBeanCollectionDataSource jrbcds = new JRBeanCollectionDataSource(listDataBeanColletionReport);
		FacesContext context = FacesContext.getCurrentInstance();
		context.responseComplete();
		ServletContext scontext = (ServletContext) context.getExternalContext().getContext();
		String caminhoRelatorio = scontext.getRealPath(FOLDER_RELATORIOS);

		// CARREGA O ARQUIVO
		File file = new File(caminhoRelatorio + SEPARATOR + nomeRelatorioJasper + PONTO + "jasper");

		if (caminhoRelatorio == null || (caminhoRelatorio == null && caminhoRelatorio.isEmpty()) || file.exists()) {

			caminhoRelatorio = this.getClass().getResource(FOLDER_RELATORIOS).getPath();
			SEPARATOR = "";
		}

		/* CAMINHO PARA IMAGENS */
		parametrosRelatorio.put("REPORT_PARAMETERS_IMG", caminhoRelatorio);

		/* CAMINHO COMPLETO ATÉ O RELATÓRIO COMPILADO INDICADO */

		String caminhoArquivoJasper = caminhoRelatorio + SEPARATOR + nomeRelatorioJasper + PONTO + "jasper";
		/* FAZ O CARREGAMENTO DO RELATORIO INDICADO */

		JasperReport relatorioJasper = (JasperReport) JRLoader.loadObjectFromFile(caminhoArquivoJasper);

		/* SETA PARAMETRO SUBREREPORT_DIR COMO CAMINHO PARA O SUB-REPORTS */

		caminhoSubreport_dir = caminhoRelatorio + SEPARATOR;
		parametrosRelatorio.put(SUBREPORT_DIR, caminhoSubreport_dir);

		/* CARREGA O ARQUIVO COMPILADO PARA A MEMÓRIA */

		JasperPrint impressoraJasper = JasperFillManager.fillReport(relatorioJasper, parametrosRelatorio, jrbcds);

		switch (tipoRelatorio) {

		case RELATORIO_PDF:
			tipoArquivoExportado = new JRPdfExporter();
			ExtensaoArquivoExportado = EXTESION_PDF;
			break;

		case RELATORIO_EXCEL:
			tipoArquivoExportado = new JRXlsExporter();
			ExtensaoArquivoExportado = EXTESION_XLS;
			break;

		case RELATORIO_HTML:
			tipoArquivoExportado = new JRHtmlExporter();
			ExtensaoArquivoExportado = EXTESION_HTML;
			break;

		case RELATORIO_PLANILHA_OPEN_0FFICE:
			tipoArquivoExportado = new JROdtExporter();
			ExtensaoArquivoExportado = EXTESION_ODS;
			break;

		default:
			tipoArquivoExportado = new JRPdfExporter();
			ExtensaoArquivoExportado = EXTESION_PDF;
			break;
		}

		nomeRelatorioSaida += UNDERLINE + DateUtils.getDateAtualReportName();
		/* CAMINHO DO RELATORIO EXPORTATO */

		caminhoArquivoRelatorio = caminhoRelatorio + SEPARATOR + nomeRelatorioSaida + PONTO + ExtensaoArquivoExportado;

		/* CRIA NOVO FILE EXPORTADO */

		arquivoGerado = new File(caminhoArquivoRelatorio);

		/* PREPARAR A IMPRESSÃO */

		tipoArquivoExportado.setParameter(JRExporterParameter.JASPER_PRINT, impressoraJasper);

		/* NOME DO ARQUIVO FÍSICO A SER IMPRESSO/EXPORTADO */

		tipoArquivoExportado.setParameter(JRExporterParameter.OUTPUT_FILE, arquivoGerado);

		/* EXECUTA A EXPORTAÇÃO */

		tipoArquivoExportado.exportReport();

		/* REMOVE O ARQUIVO DO SERVIDOR APÓS SER VEITO O DOWNLOAD PELO USUARIO */
		arquivoGerado.deleteOnExit();

		/* CRIA O INPUTSTREAM PARA SER USADO PELO PRIMAREFACES */
		InputStream conteudoRelatorio = new FileInputStream(arquivoGerado);

		/* FAZ O RETORNO PARA A APLICAÇÃO */
		arquivoRetorno = new DefaultStreamedContent(conteudoRelatorio, "aplication/" + ExtensaoArquivoExportado,
				nomeRelatorioSaida + PONTO + ExtensaoArquivoExportado);

		return arquivoRetorno;

	}

}
