<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html ng-app="myApp">
<head lang="en">
    <meta charset="UTF-8">
    <link rel="stylesheet" href="webjars/bootstrap/3.2.0/css/bootstrap.css">
    <link rel="stylesheet" href="webjars/bootstrap-material-design/0.2.1/css/material.css">
    <title>AngularJS</title>
    
     <style>
      .username.ng-valid {
          background-color: lightgreen;
      }
      .username.ng-dirty.ng-invalid-required {
          background-color: red;
      }
      .username.ng-dirty.ng-invalid-minlength {
          background-color: yellow;
      }

      .email.ng-valid {
          background-color: lightgreen;
      }
      .email.ng-dirty.ng-invalid-required {
          background-color: red;
      }
      .email.ng-dirty.ng-invalid-email {
          background-color: yellow;
      }

    </style>
    
     <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
     <link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
    
</head>
<body ng-controller="UsersController">

<div class="row">
    <br>
    <div class="container">
        <div id="userList" class="col-sm-offset-1 col-sm-10">
            <div class="input-group">
                <input class="form-control" id="search" name="search" placeholder="Search for" ng-model="query"
                       required="required"/>
              <span class="input-group-btn">
                  <button type="submit" class="btn btn-default">
                      <i class="glyphicon glyphicon-search"></i>
                  </button>
              </span>
            </div>
            <div class="list-group">
                <div class="list-group-item">
                    <div ng-repeat="user in users | filter:query" class="list-group-item" style="margin-top:16px">
                        <div class="row-picture">
                            <img class="circle"
                                 src="http://i.forbesimg.com/media/lists/people/{{user.firstName | lowercase}}-{{user.familyName | lowercase}}_50x50.jpg"
                                 alt="icon">
                        </div>
                        <div class="row-content">
                            <h4 class="list-group-item-heading">{{user.firstName}} {{user.familyName}}</h4>

                            <p class="list-group-item-text"><i class="glyphicon glyphicon-envelope"></i> {{user.email}}
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>









      <div class="generic-container" ng-controller="UserController as ctrl">
          <div class="panel panel-default">
              <div class="panel-heading"><span class="lead">User Registration Form </span></div>
              <div class="formcontainer">
                  <form ng-submit="ctrl.submit()" name="myForm" class="form-horizontal">
                      <input type="hidden" ng-model="ctrl.user.id" />
                      <div class="row">
                          <div class="form-group col-md-12">
                              <label class="col-md-2 control-lable" for="file">Name</label>
                              <div class="col-md-7">
                                  <input type="text" ng-model="ctrl.user.username" name="uname" class="username form-control input-sm" placeholder="Enter your name" required ng-minlength="3"/>
                                  <div class="has-error" ng-show="myForm.$dirty">
                                      <span ng-show="myForm.uname.$error.required">This is a required field</span>
                                      <span ng-show="myForm.uname.$error.minlength">Minimum length required is 3</span>
                                      <span ng-show="myForm.uname.$invalid">This field is invalid </span>
                                  </div>
                              </div>
                          </div>
                      </div>
                        
                      
                      <div class="row">
                          <div class="form-group col-md-12">
                              <label class="col-md-2 control-lable" for="file">Address</label>
                              <div class="col-md-7">
                                  <input type="text" ng-model="ctrl.user.address" class="form-control input-sm" placeholder="Enter your Address. [This field is validation free]"/>
                              </div>
                          </div>
                      </div>

                      <div class="row">
                          <div class="form-group col-md-12">
                              <label class="col-md-2 control-lable" for="file">Email</label>
                              <div class="col-md-7">
                                  <input type="email" ng-model="ctrl.user.email" name="email" class="email form-control input-sm" placeholder="Enter your Email" required/>
                                  <div class="has-error" ng-show="myForm.$dirty">
                                      <span ng-show="myForm.email.$error.required">This is a required field</span>
                                      <span ng-show="myForm.email.$invalid">This field is invalid </span>
                                  </div>
                              </div>
                          </div>
                      </div>

                      <div class="row">
                          <div class="form-actions floatRight">
                              <input type="submit"  value="{{!ctrl.user.id ? 'Add' : 'Update'}}" class="btn btn-primary btn-sm" ng-disabled="myForm.$invalid">
                              <button type="button" ng-click="ctrl.reset()" class="btn btn-warning btn-sm" ng-disabled="myForm.$pristine">Reset Form</button>
                          </div>
                      </div>
                  </form>
              </div>
          </div>
          <div class="panel panel-default">
                <!-- Default panel contents -->
              <div class="panel-heading"><span class="lead">List of Users </span></div>
              <div class="tablecontainer">
                  <table class="table table-hover">
                      <thead>
                          <tr>
                              <th>ID.</th>
                              <th>Name</th>
                              <th>Address</th>
                              <th>Email</th>
                              <th width="20%"></th>
                          </tr>
                      </thead>
                      <tbody>
                          <tr ng-repeat="u in ctrl.users">
                              <td><span ng-bind="u.id"></span></td>
                              <td><span ng-bind="u.username"></span></td>
                              <td><span ng-bind="u.address"></span></td>
                              <td><span ng-bind="u.email"></span></td>
                              <td>
                              <button type="button" ng-click="ctrl.edit(u.id)" class="btn btn-success custom-width">Edit</button>  <button type="button" ng-click="ctrl.remove(u.id)" class="btn btn-danger custom-width">Remove</button>
                              </td>
                          </tr>
                      </tbody>
                  </table>
              </div>
          </div>
      </div>






















<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular.js"></script>

<script type="text/javascript" src="webjars/jquery/2.1.1/jquery.js"></script>
<script type="text/javascript" src="webjars/angularjs/1.3.8/angular.min.js"></script>
<script type="text/javascript" src="webjars/angularjs/1.3.8/angular-resource.min.js"></script>
<script type="text/javascript" src="webjars/bootstrap-material-design/0.2.1/js/material.js"></script>
<script type="text/javascript" src="resources/js/app.js"></script>


 <script type="text/javascript" src="<c:url value='/static/js/app.js' />"></script>
 <script type="text/javascript" src="<c:url value='/static/js/service/user_service.js' />"></script>
 <script type="text/javascript" src="<c:url value='/static/js/controller/user_controller.js' />"></script>


</body>
</html>