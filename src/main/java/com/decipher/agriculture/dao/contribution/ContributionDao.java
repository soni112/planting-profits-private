package com.decipher.agriculture.dao.contribution;

import com.decipher.agriculture.data.Contribution;

/**
 * Created by Raja Dushyant Vashishtha
 */
public interface ContributionDao {

    void save(Contribution contribution);

    Contribution update(Contribution contribution);

    boolean delete(Contribution contribution);

    Contribution getById(Long id);
}
