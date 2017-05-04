<%--
  Created by IntelliJ IDEA.
  User: abhishek
  Date: 28/9/16
  Time: 4:52 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<section class="jumbotron" style="padding-bottom: 0; margin-bottom: 0; ">
    <div class="container">
        <div class="row ">
            <!---- Blog List ----->
            <div class="jumbotron text-center" style="padding: 0">
                <h1>Planting Profits Training</h1>
            </div>
            <div class="col-lg-8 col-sm-8 col-md-8">
                <div class="tutorial-single-content">
                    <!-- POST -->
                    <div class="tutorial-pg tutorial-single">

                        <div class="tutorial-media">
                            <div class="video embed-responsive embed-responsive-16by9">
                                <video id="videoPlayer" width="100%" height="100%"
                                       src="http://plantingprofits.com:82/module1.mp4"
                                       type="video/mp4"
                                       controls ></video>
                            </div>
                        </div>


                        <%--<div class="tutorial-title">
                            <h3 class="big">How to Create Course in this theme? This is title of the blog</h3>
                        </div>
                        <div class="tutorial-meta">
                            by <a href="#">Brett Todd</a> on October 7, 2014
                        </div>
                        <div class="pdf-sec">
                            <a href="" type="button" class="btn-dwn-pdf"><i class="fa fa-download"></i> &nbsp;Download
                                PDF</a>
                            <a href="" type="button" class="btn-dwn-pdf"><i class="fa fa-eye"></i> &nbsp;Show PDF</a>
                            <a href="" type="button" class="btn-dwn-pdf"><i class="fa fa-share"></i> &nbsp;Share</a>
                        </div>--%>
                    </div>
                    <!-- END / POST -->
                    <!-- WIDGET CATEGORIES -->
                    <%--<div class="tutorial-content">
                        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut laoreet nulla sed venenatis
                            vulputate. Nulla venenatis mi sed fermentum laoreet. Aenean consequat eros nec sem ultrices
                            tempus. In fringilla porttitor lectus in aliquet. Aliquam elit ipsum, accumsan vitae
                            sagittis eget, ultricies at purus. Pellentesque neque libero, dapibus et rhoncus sed,
                            vestibulum et dui. Pellentesque habitant morbi tristique senectus et netus et malesuada
                            fames ac turpis egestas.
                            <br>
                            In sed pellentesque leo. Nunc non ipsum ex. Sed mattis sem sit amet orci dapibus, eget
                            feugiat urna porttitor. Etiam tincidunt at ex quis iaculis. Maecenas consectetur porttitor
                            est et mollis. Sed in vehicula ligula. In interdum ut orci dapibus semper. Quisque tristique
                            risus ut augue rutrum, vitae faucibus felis interdum. Mauris sagittis sem vel dignissim
                            porta. Nullam sit amet risus ut purus eleifend tempor quis in sem. Morbi ac hendrerit purus.
                            Vivamus venenatis consequat viverra. Lorem ipsum dolor sit amet, consectetur adipiscing
                            elit. Curabitur tincidunt urna ut lobortis tristique. Nunc in urna non leo vehicula rhoncus
                            ac in sapien.</p>
                        <h4>Step 1 : Embrace yourself, winter is coming</h4>
                        <ol>
                            <li>Sed in vehicula ligula. In interdum ut orci dapibus semper. Quisque tristique risus ut
                                augue rutrum, vitae faucibus felis interdum.
                            </li>
                            <li>Sed in vehicula ligula. In interdum ut orci dapibus semper. Quisque tristique risus ut
                                augue rutrum, vitae faucibus felis interdum.
                            </li>
                            <li>Sed in vehicula ligula. In interdum ut orci dapibus semper. Quisque tristique risus ut
                                augue rutrum, vitae faucibus felis interdum.
                            </li>
                        </ol>
                        <h4>Step 2 : This is a joke, don’t read</h4>
                        <ul>
                            <li>Mauris sagittis sem vel dignissim porta Curabitur tincidunt urna ut lobortis tristique.
                                Nunc in urna non leo vehicula rhoncus ac in sapien.
                            </li>
                            <li>Nullam sit amet risus ut purus eleifend tempor quis in sem. Morbi ac hendrerit purus.
                                Vivamus venenatis consequat viverra. Lorem ipsum dolor sit amet, consectetur adipiscing
                                elit.
                            </li>
                        </ul>
                        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut laoreet nulla sed venenatis
                            vulputate. Nulla venenatis mi sed fermentum laoreet. Aenean consequat eros nec sem ultrices
                            tempus. In fringilla porttitor lectus in aliquet. Aliquam elit ipsum, accumsan vitae
                            sagittis eget, ultricies at purus. Pellentesque neque libero, dapibus et rhoncus sed,
                            vestibulum et dui. Pellentesque habitant morbi tristique senectus et netus et malesuada
                            fames ac turpis egestas.</p>
                        <blockquote>
                            <p>"In fringilla porttitor lectus in aliquet. Aliquam elit ipsum, accumsan vitae sagittis
                                eget, ultricies at purus. Pellentesque neque libero, dapibus et rhoncus sed, vestibulum
                                et dui. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac
                                turpis egestas."</p>
                            <cite>Robet Smith</cite>
                        </blockquote>
                    </div>--%>
                </div>
                <!-- END / RELATED POSTS -->


            </div>
            <!-- END / BLOG LIST -->

            <div class="col-md-3 col-sm-3 col-md-offset-1">
                <aside class="tutorial-sidebar">
                    <!-- WIDGET SEARCH -->
                    <%--<div class="widget widget_search">
                        <h4 class="sm">Search in Blog</h4>
                        <form>
                            <div class="form-item">
                                <input type="text">
                            </div>
                            <div class="form-actions">
                                <input type="submit">
                            </div>
                        </form>
                    </div>--%>
                    <!-- END / WIDGET SEARCH -->

                    <!-- Training Video 1 -->
                    <div class="about-author">
                        <a href="#" onclick="switchVideo(this); return false;"
                           data-link="http://plantingprofits.com:82/module1.mp4">
                            <div class="image-thumb fl">
                                <img src="<c:url value="/images/training/planting_training1.png"/>" alt="Module 1 - Introduction to Planting Profits">
                            </div>
                            <div class="author-info">
                                <h5 class="">Module 1</h5>
                                <div class="author-content">
                                    <p>Introduction to Planting Profits</p>
                                </div>
                            </div>
                        </a>
                    </div>
                    <!-- END / Training Video 1 -->

                    <!-- Training Video 2 -->
                    <div class="about-author">
                        <a href="#" onclick="switchVideo(this); return false;"
                        data-link="http://plantingprofits.com:82/module2.mp4">
                            <div class="image-thumb fl">
                                <img src="<c:url value="/images/training/planting_training2.png"/>" alt="Module 2 - Navigation and Terminology">
                            </div>
                            <div class="author-info">
                                <h5 class="">Module 2</h5>
                                <div class="author-content">
                                    <p>Navigation and Terminology</p>
                                </div>
                            </div>
                        </a>
                    </div>
                    <!-- END / Training Video 2 -->

                    <!-- Training Video 3 -->
                    <div class="about-author">
                        <a href="#" onclick="switchVideo(this); return false;"
                           data-link="http://plantingprofits.com:82/module3.mp4">
                            <div class="image-thumb fl">
                                <img src="<c:url value="/images/training/planting_training3.png"/>" alt="Module 3 - Collecting Farm Information">
                            </div>
                            <div class="author-info">
                                <h5 class="">Module 3</h5>
                                <div class="author-content">
                                    <p>Collecting Farm Information</p>
                                </div>
                            </div>
                        </a>
                    </div>
                    <!-- END / Training Video 3 -->

                    <!-- Training Video 4A -->
                    <div class="about-author">
                        <a href="#" onclick="switchVideo(this); return false;"
                           data-link="http://plantingprofits.com:82/module4A.mp4">
                            <div class="image-thumb fl">
                                <img src="<c:url value="/images/training/planting_training3.png"/>" alt="Module 4 - Entering Farm Information and Generating a Baseline Strategy">
                            </div>
                            <div class="author-info">
                                <h5 class="">Module 4A</h5>
                                <div class="author-content">
                                    <p>Entering Farm Information and Generating a Baseline Strategy</p>
                                </div>
                            </div>
                        </a>
                    </div>
                    <!-- END / Training Video 4A -->

                    <!-- Training Video 4B -->
                    <div class="about-author">
                        <a href="#" onclick="switchVideo(this); return false;"
                           data-link="http://plantingprofits.com:82/module4B.mp4">
                            <div class="image-thumb fl">
                                <img src="<c:url value="/images/training/planting_training3.png"/>" alt="Module 4 - Entering Farm Information and Generating a Baseline Strategy">
                            </div>
                            <div class="author-info">
                                <h5 class="">Module 4B</h5>
                                <div class="author-content">
                                    <p>Entering Farm Information and Generating a Baseline Strategy</p>
                                </div>
                            </div>
                        </a>
                    </div>
                    <!-- END / Training Video 4B     -->

                </aside>
            </div>
            <!-- END / SIDEBAR -->


        </div>
    </div>
</section>

<script type="text/javascript" src="<c:url value="/js/agriculture/training/training.js"/>"></script>