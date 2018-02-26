package com.decipher.agriculture.service.contribution.impl;

import com.decipher.agriculture.dao.contribution.ContributionDao;
import com.decipher.agriculture.data.Contribution;
import com.decipher.agriculture.data.account.Account;
import com.decipher.agriculture.service.account.AccountService;
import com.decipher.agriculture.service.contribution.ContributionService;
import com.decipher.agriculture.service.util.HTTPService;
import com.decipher.util.PlantingProfitLogger;
import com.stripe.model.Charge;
import org.apache.http.NameValuePair;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Raja Dushyant Vashishtha
 */
@Service
public class ContributionServiceImpl implements ContributionService {

    private static final String SALESFORE_LOGIN_BASE_URL = "https://login.salesforce.com/services/oauth2/token?";
    private static final String SALESFORE_LOGIN_URL = "grant_type=password&client_id=3MVG9uudbyLbNPZO68Vr2W0VNm64sXeX87in0ULaWC0XbbCTRGcrTPY82k6w7JW3q7EabnOkGlFvXHRczKGDO&client_secret=1123967193236191866&username=gary.schneider@plantingprofits.com&password=Kittycs13$1BwVCQH35SEY3N6SZJYmE8tH";
    private static final String SALESFORCE_CONTRIBUTION_PAYMENT_URL = "https://na30.salesforce.com/services/apexrest/getDetails/";

    @Autowired
    private ContributionDao contributionDao;
    @Autowired
    private AccountService accountService;
    @Autowired
    private HTTPService httpService;

    @Override
    public Contribution createContribution(Charge charge) {
        Contribution contribution = new Contribution();
        contribution.setAccount(accountService.getCurrentUser());
        contribution.setChargeId(charge.getId());
        contribution.setAmount(charge.getAmount());
        contribution.setAmountRefunded(charge.getAmountRefunded());
        contribution.setApplicationFee(charge.getApplicationFee());
        contribution.setBalanceTransaction(charge.getBalanceTransaction());
        contribution.setCaptured(charge.getCaptured());
        contribution.setCreated(charge.getCreated());
        contribution.setCurrency(charge.getCurrency());
        contribution.setCustomer(charge.getCustomer());
        contribution.setDescription(charge.getDescription());
        contribution.setDestination(charge.getDestination());
        contribution.setFailureCode(charge.getFailureCode());
        contribution.setFailureMessage(charge.getFailureMessage());
        contribution.setInvoice(charge.getInvoice());
        contribution.setLivemode(charge.getLivemode());
        contribution.setOrder(charge.getOrder());
        contribution.setPaid(charge.getPaid());
        contribution.setReceiptEmail(charge.getReceiptEmail());
        contribution.setReceiptNumber(charge.getReceiptNumber());
        contribution.setRefunded(charge.getRefunded());
        contribution.setSourceTransfer(charge.getSourceTransfer());
        contribution.setStatementDescriptor(charge.getStatementDescriptor());
        contribution.setStatus(charge.getStatus());
        contribution.setTransfer(charge.getTransfer());
        return contribution;
    }

    @Override
    public void save(Contribution contribution) {
        contributionDao.save(contribution);
    }

    @Override
    public Contribution update(Contribution contribution) {
        return contributionDao.update(contribution);
    }

    @Override
    public boolean delete(Contribution contribution) {
        return contributionDao.delete(contribution);
    }

    @Override
    public Contribution getById(Long id) {
        return contributionDao.getById(id);
    }

    @Override
    public JSONObject getContributionFromSalesForce() {

        try {
            Account currentUser = accountService.getCurrentUser();

            List<NameValuePair>  nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("grant_type", "password"));
            nameValuePairs.add(new BasicNameValuePair("client_id", "3MVG9uudbyLbNPZO68Vr2W0VNm64sXeX87in0ULaWC0XbbCTRGcrTPY82k6w7JW3q7EabnOkGlFvXHRczKGDO"));
            nameValuePairs.add(new BasicNameValuePair("client_secret", "1123967193236191866"));
            nameValuePairs.add(new BasicNameValuePair("username", "gary.schneider@plantingprofits.com"));
            nameValuePairs.add(new BasicNameValuePair("password", "Kittycs13$1BwVCQH35SEY3N6SZJYmE8tH "));


            JSONParser parser = new JSONParser();
//            JSONObject response = (JSONObject) parser.parse(httpService.sendHttpsPost(SALESFORE_LOGIN_BASE_URL + URLEncoder.encode(SALESFORE_LOGIN_URL, "UTF-8"), null));
            JSONObject response = (JSONObject) parser.parse(httpService.sendHttpsPost(SALESFORE_LOGIN_BASE_URL + SALESFORE_LOGIN_URL, nameValuePairs));
            String accessToken = response.get("access_token").toString();
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Bearer " + accessToken);
            String s = httpService.sendHttpsGet(SALESFORCE_CONTRIBUTION_PAYMENT_URL + currentUser.getEmail_Address(), null, headers);
            return (JSONObject) parser.parse(s);
        } catch (Exception e) {
            PlantingProfitLogger.error(e);
            return null;
        }

    }


}