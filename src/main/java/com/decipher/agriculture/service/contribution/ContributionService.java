package com.decipher.agriculture.service.contribution;

import com.decipher.agriculture.data.Contribution;
import com.stripe.model.Charge;
import org.json.simple.JSONObject;

/**
 * Created by Raja Dushyant Vashishtha
 */
public interface ContributionService {

    Contribution createContribution(Charge charge);

    void save(Contribution contribution);

    Contribution update(Contribution contribution);

    boolean delete(Contribution contribution);

    Contribution getById(Long id);

    JSONObject getContributionFromSalesForce();

}
