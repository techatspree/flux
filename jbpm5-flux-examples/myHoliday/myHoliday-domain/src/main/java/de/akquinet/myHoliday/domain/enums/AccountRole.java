package de.akquinet.myHoliday.domain.enums;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
public enum AccountRole implements I18nEnum {

	TECHNICALADMIN("myHoliday.accountRole.technicaladmin", "Admin"), ADMINISTRATION(
			"myHoliday.accountRole.administration", "administration"), USER(
			"myHoliday.accountRole.user", "user");

	private final String i18nDescriptionId;
	private final String roleId;

	private AccountRole(final String i18nDescriptionId, final String roleId) {
		this.i18nDescriptionId = i18nDescriptionId;
		this.roleId = roleId;
	}

	@Override
	public String getI18nDescriptionId() {
		return i18nDescriptionId;
	}

	public String getRoleId() {
		return this.roleId;
	}
}
