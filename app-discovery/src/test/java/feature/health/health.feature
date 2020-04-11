#Author: pratim.ghosh@outlook.com
@tag
Feature: Discovery Dashboard

  Background:
    * url 'https://localhost:8761'
    * configure ssl = true
  
  @home
  Scenario: home page loads
    Given path '/'
    When method GET
    Then status 200

