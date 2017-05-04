package com.decipher.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.social.security.SocialUser;
import com.decipher.agriculture.data.account.AppRole;

public class CustomUserDetails extends SocialUser {
	private int id;
	private String name;
	private AppRole role;

    /**
     * @changed - Abhishek
     * @date - 02-04-2016
     * @desc - for enable and disable feature of account
     */
    private boolean enabled;

    public CustomUserDetails(String username, String password,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}

    /**
     * @changed - Abhishek
     * @date - 02-04-2016
     * @desc - for providing enabled user to spring security
     */
    public CustomUserDetails(String username, String password, boolean enabled,
                             Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, true, true, true, authorities);
    }


	public static Builder getBuilder() {
		return new Builder();
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public AppRole getRole() {
		return role;
	}

	public boolean getEnabled() {
		return enabled;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("id", id)
				.append("username", getUsername()).append("name", name)
				.append("role", role).toString();
	}

	public static class Builder {

		private int id;

		private String username;
		private String name;

		private String password;

		private AppRole role;

        /**
         * @changed - Abhishek
         * @date - 02-04-2016
         * @desc - for enable and disable feature of account
         */
        private boolean enabled;

		private Set<GrantedAuthority> authorities;

		public Builder() {
			this.authorities = new HashSet<GrantedAuthority>();
		}

		public Builder id(int id) {
			this.id = id;
			return this;
		}

		public Builder password(String password) {
			if (password == null) {
				password = "SocialUser";
			}

			this.password = password;
			return this;
		}

		public Builder role(AppRole role) {
			this.role = role;

			SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.toString());
			this.authorities.add(authority);

			return this;
		}

		public Builder username(String username) {
			this.username = username;
			return this;
		}

		public Builder name(String name) {
			this.name = name;
			return this;
		}

        public Builder enabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public CustomUserDetails build() {
            /**
             * @changed - Abhishek
             * @date - 02-04-2016
             * @desc - for enable and disable feature of account
             */
			/*CustomUserDetails user = new CustomUserDetails(username, password, authorities);*/
            CustomUserDetails user = new CustomUserDetails(username, password, enabled, authorities);
			user.id = id;
			user.name = name;
			user.role = role;

            user.enabled = enabled;
			return user;
		}
	}
}
