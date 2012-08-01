package de.akquinet.myHoliday.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
@Entity
@NamedQueries({
		@NamedQuery(name = UserAccount.QUERY_FIND_BY_USERNAME, query = "Select u FROM UserAccount u WHERE u.userName = :"
				+ UserAccount.PARAM_USERNAME),
		@NamedQuery(name = UserAccount.QUERY_FIND_BY_USERNAME_AND_PASSWORD, query = "Select u FROM UserAccount u WHERE u.userName = :"
				+ UserAccount.PARAM_USERNAME
				+ " and u.password = :"
				+ UserAccount.PARAM_PASSWORD) })
public class UserAccount extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	public static final String QUERY_FIND_BY_USERNAME = "findByUserName";
	public static final String QUERY_FIND_BY_USERNAME_AND_PASSWORD = "findByUserNameAndPassword";

	public static final String PARAM_USERNAME = "userName";
	public static final String PARAM_PASSWORD = "password";

	@NotNull
	@Column(unique = true)
	private String userName;

	@NotNull
	private String firstName;

	@NotNull
	private String lastName;

	@NotNull
	private String password;

	@NotNull
	@Pattern(regexp = ".+@.+\\.[a-z]+")
	@Column(unique = true)
	private String eMail;

	@NotNull
	@Pattern(regexp = "[0-9]+")
	private String phoneNumber;

	@JoinTable(name = "benutzer_rollen", joinColumns = { @JoinColumn(name = "benutzer", referencedColumnName = ID_COLUMN) }, inverseJoinColumns = { @JoinColumn(name = "rollen", referencedColumnName = ID_COLUMN) })
	@ManyToMany(fetch = FetchType.EAGER)
	private List<UserAccountRole> userAccountRoles;

	public UserAccount() {
	}

	public UserAccount(final String userName, final String firstName,
			final String lastName, final String password, final String eMail,
			final String phoneNumber, final List<UserAccountRole> userAccountRoles) {
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.eMail = eMail;
		this.phoneNumber = phoneNumber;
		this.userAccountRoles = userAccountRoles;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(final String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(final String eMail) {
		this.eMail = eMail;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public List<UserAccountRole> getUserAccountRoles() {
		return userAccountRoles;
	}

	public void setUserAccountRoles(final List<UserAccountRole> userAccountRoles) {
		this.userAccountRoles = userAccountRoles;
	}

	public void setUserAccountRole(final UserAccountRole userAccountRole) {
		if (!this.userAccountRoles.contains(userAccountRole)) {
			this.userAccountRoles.add(userAccountRole);
		}
	}

	public void removeUserAccountRole(final UserAccountRole userAccountRole) {
		if (this.userAccountRoles.contains(userAccountRole)) {
			this.userAccountRoles.remove(userAccountRole);
		}
	}

	public String getUserAccountRolesAsString() {
		String userAccountRolesAsString = "";
		for (final UserAccountRole userAccountRole : this.userAccountRoles) {
			userAccountRolesAsString = userAccountRole.getAccountRole().getRoleId()
					+ " " + userAccountRolesAsString;
		}
		return userAccountRolesAsString;
	}
}
