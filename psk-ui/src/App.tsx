import React from "react";

import { Layout, Menu, Icon } from "antd";
import { Route, Link, Switch } from "react-router-dom";
import { TEAM_NAME, CALENDAR_ITEM_NAME, USER_ITEM_NAME } from "./Constants";

import CalendarView from "./pages/public/calendar-view";
import LandingPage from "./pages/public/landing";

import "./App.css";

const { Content, Footer, Sider } = Layout;

const App: React.FunctionComponent<{}> = () => {
  return (
    <div className="App">
      <Layout id="root-layout">
        <Sider id="sider">
          <div className="logo" />
          <Menu theme="dark" mode="inline" defaultSelectedKeys={["4"]}>
            <Menu.Item key="1">
              <Link to="/profile">
                <Icon type="user" />
                <span className="nav-text">{USER_ITEM_NAME}</span>
              </Link>
            </Menu.Item>
            <Menu.Item key="2">
              <Link to="/calendar">
                <Icon type="calendar" />
                <span className="nav-text">{CALENDAR_ITEM_NAME}</span>
              </Link>
            </Menu.Item>
          </Menu>
        </Sider>
        <Layout id="main-content-layout">
          <Content>
            <Switch>
              <Route exact path="/" component={LandingPage} />
              <Route exact path="/calendar" component={CalendarView} />
              <Route exact path="/calendar/:year/:month/:day" />
              <Route exact path="/profile" />
            </Switch>
          </Content>
          <Footer>Powered By: {TEAM_NAME}</Footer>
        </Layout>
      </Layout>
    </div>
  );
};

export default App;
