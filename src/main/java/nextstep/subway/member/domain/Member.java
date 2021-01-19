package nextstep.subway.member.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.StringUtils;

import nextstep.subway.BaseEntity;
import nextstep.subway.auth.application.AuthorizationException;
import nextstep.subway.common.exception.NoFavoriteExistException;
import nextstep.subway.favorite.domain.Favorite;

@Entity
public class Member extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String email;
	private String password;
	private Integer age;

	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Favorite> favorites = new ArrayList<>();

	public Member() {
	}

	public Member(String email, String password, Integer age) {
		this.email = email;
		this.password = password;
		this.age = age;
	}

	public void update(Member member) {
		this.email = member.email;
		this.password = member.password;
		this.age = member.age;
	}

	public void checkPassword(String password) {
		if (!StringUtils.equals(this.password, password)) {
			throw new AuthorizationException();
		}
	}

	public void addFavorite(Favorite favorite) {
		this.favorites.add(favorite);
	}

	public void removeFavorite(Long favoriteId) {
		Favorite target = this.favorites.stream()
			.filter(favorite -> favorite.isEqualId(favoriteId))
			.findFirst()
			.orElseThrow(NoFavoriteExistException::new);
		favorites.remove(target);
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public Integer getAge() {
		return age;
	}

	public List<Favorite> getFavorites() {
		return favorites;
	}

}
