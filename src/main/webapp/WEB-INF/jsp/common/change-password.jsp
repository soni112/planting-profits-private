<script src="js/agriculture/change-password.js" type="text/javascript"></script>
<!-- Popup Div Start Here Change Password -->
<div id="ChangePasswordPopUp">
    <div id="popupContact">
        <!-- Contact Us Form -->
        <div class="popup_section">
            <img onclick="div_hide9()" src="images/cross.png" id="close">
            <h2 class="popupheader">Change Password</h2>

            <div class="popupform">

                <div class="form-group">
                    <label>Current Password</label>
                    <input type="password" class="form-control" placeholder="Enter your current password"
                           id="user_current_password"/>
                </div>
                <div class="form-group">
                    <label>New Password</label>
                    <input type="password" class="form-control" placeholder="Enter new password"
                           id="user_new_password"/>
                </div>
                <div class="form-group">
                    <label>Confirm Password</label>
                    <input type="password" class="form-control" placeholder="Enter confirm password"
                           id="user_confirm_password"/>
                </div>
                <div class="yellobtn submit">
                    <a onclick="changeUserPassword()">Update</a>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Popup Div End Here Change Password -->