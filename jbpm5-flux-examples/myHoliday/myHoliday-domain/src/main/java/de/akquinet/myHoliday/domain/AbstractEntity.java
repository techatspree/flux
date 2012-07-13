package de.akquinet.myHoliday.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String VERSION_COLUMN = "LAST_SAVED";
	public static final String ID_COLUMN = "ID";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = ID_COLUMN)
	private Long id;

	@Version
	@Column(name = VERSION_COLUMN)
	private Date version;

	public AbstractEntity() {
		// NOP
	}

	public Long getId() {
		return id;
	}

	public Date getVersion() {
		return version;
	}

	/**
	 * Never override these methods when using JPA/Hibernate
	 */
	@Override
	public final boolean equals(final Object obj) {
		return super.equals(obj);
	}

	/**
	 * Never override these methods when using JPA/Hibernate
	 */
	@Override
	public final int hashCode() {
		return super.hashCode();
	}

}
