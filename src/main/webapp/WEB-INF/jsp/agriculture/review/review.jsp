<%--
  Created by IntelliJ IDEA.
  User: Sushil
  Date: 08-Sep-17
  Time: 6:05 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container">
    <div class="row">
        <div class="col-lg-12 col-md-12">
            <div class="jumbotron margin-top-2 pull-left m-b-2">

                <h2 class="text-center font-weight-600">Add Report an Issue feature</h2>
                <br>
                <div class="col-lg-12 col-md-12 col-sm-12">

                    <form>
                       <%-- <div class="form-group form-group1">
                            <label>Date<span class="strickColor">*</span></label>
                            <input type="text" class="form-control" placeholder="Date" />
                        </div>
                        <div class="form-group form-group1">
                            <label>Time<span class="strickColor">*</span></label>
                            <input type="text" class="form-control" placeholder="Time" />
                        </div>
                        <div class="form-group form-group1">
                            <label>Type of User<span class="strickColor">*</span></label>
                            <input type="text" class="form-control" placeholder="Type of User" />
                        </div>--%>
                        <div class="form-group form-group1">
                            <label>Name</label>
                            <input type="text" class="form-control last-name" placeholder="Name" />
                        </div>
                        <div class="form-group form-group1">
                            <label>Email id<span class="strickColor">*</span></label>
                            <input type="text" class="form-control email-id" placeholder="Email address" />
                        </div>
                        <div class="form-group form-group1">
                            <label>Phone<span class="strickColor">*</span></label>
                            <input type="text" class="form-control contact-no" placeholder="Phone Number" />
                        </div>
                        <hr>
                        <div class="form-group form-group1">
                            <label>Address Line1</label>
                            <input type="text" class="form-control" placeholder=" Address Line1" />
                        </div>
                        <div class="form-group form-group1">
                            <label>Address Line2</label>
                            <input type="text" class="form-control" placeholder="Address Line2" />
                        </div>

                        <div class="form-group form-group1">
                            <label>Address State</label>

                            <select class="form-control">
                                <option value="" selected="">Select Country First</option>
                            </select>
                        </div>
                        <div class="form-group form-group1">
                            <label>Address City/Town</label>
                            <input type="text" class="form-control" placeholder="City/Town" />
                        </div>
                        <div class="form-group form-group1">
                            <label>Zip</label>
                            <input type="text" class="form-control" placeholder= Zip" />
                        </div>

                        <div class="clearfix"></div>
                        <div class="form-group" style="padding: 0% 2%;">
                            <label>Issue</label>
                            <textarea class="form-control" rows="5" cols="8" placeholder="Write your Issue..." style="resize: none;"></textarea>
                        </div>

                        <div class="clearfix"></div>
                        <div class="form-group" style="padding: 0% 2%;">
                            <input type="button" class="alertify-button alertify-button-ok pull-right" value="Send">
                            <input type="reset" class="alertify-button alertify-button-ok pull-right" value="Reset">
                        </div>
                    </form>

                </div>
            </div>
        </div>
    </div>
</div>
