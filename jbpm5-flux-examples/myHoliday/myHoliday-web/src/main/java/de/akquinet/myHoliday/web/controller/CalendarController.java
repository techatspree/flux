package de.akquinet.myHoliday.web.controller;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
@Named
@SessionScoped
@Stateful
public class CalendarController implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String[] WEEK_DAY_LABELS = new String[] { "Sun *",
			"Mon +", "Tue +", "Wed +", "Thu +", "Fri +", "Sat *" };
	private Locale locale;

	private boolean popup;
	private boolean showInput;
	private boolean enableManualInput;
	private String pattern;
	private Date currentDate;
	private Date selectedDate;
	private String jointPoint;
	private String direction;
	private String boundary;

	private boolean useCustomDayLabels;

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public boolean isPopup() {
		return popup;
	}

	public void setPopup(boolean popup) {
		this.popup = popup;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public CalendarController() {
		locale = Locale.GERMANY;
		popup = true;
		pattern = "dd.MM.yyyy";
		jointPoint = "bottomleft";
		direction = "bottomright";
		enableManualInput = true;
		showInput = true;
		boundary = "inactive";
	}

	public boolean isShowInput() {
		return showInput;
	}

	public void setShowInput(boolean showInput) {
		this.showInput = showInput;
	}

	public boolean isEnableManualInput() {
		return enableManualInput;
	}

	public void setEnableManualInput(boolean enableManualInput) {
		this.enableManualInput = enableManualInput;
	}

	public void selectLocale(ValueChangeEvent event) {
		String tLocale = (String) event.getNewValue();
		if (tLocale != null) {
			String lang = tLocale.substring(0, 2);
			String country = tLocale.substring(3);
			locale = new Locale(lang, country, "");
		}
	}

	public boolean isUseCustomDayLabels() {
		return useCustomDayLabels;
	}

	public void setUseCustomDayLabels(boolean useCustomDayLabels) {
		this.useCustomDayLabels = useCustomDayLabels;
	}

	public Object getWeekDayLabelsShort() {
		if (isUseCustomDayLabels()) {
			return WEEK_DAY_LABELS;
		} else {
			return null;
		}
	}

	public String getCurrentDateAsText() {
		Date currentDate = getCurrentDate();
		if (currentDate != null) {
			return DateFormat.getDateInstance(DateFormat.FULL).format(currentDate);
		}

		return null;
	}

	public Date getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}

	public Date getSelectedDate() {
		return selectedDate;
	}

	public void setSelectedDate(Date selectedDate) {
		this.selectedDate = selectedDate;
	}

	public String getJointPoint() {
		return jointPoint;
	}

	public void setJointPoint(String jointPoint) {
		this.jointPoint = jointPoint;
	}

	public void selectJointPoint(ValueChangeEvent event) {
		jointPoint = (String) event.getNewValue();
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public void selectDirection(ValueChangeEvent event) {
		direction = (String) event.getNewValue();
	}

	public String getBoundary() {
		return boundary;
	}

	public void setBoundary(String boundary) {
		this.boundary = boundary;
	}

}
