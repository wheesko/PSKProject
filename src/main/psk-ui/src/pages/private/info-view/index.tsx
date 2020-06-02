import React, { useEffect, useState } from 'react';

import { Button, Card, Col, Row, Spin, Table, Typography } from 'antd';

import './InfoStyles.css';
import { TeamsByTopics } from "./teams-by-topics";
const InfoView: React.FunctionComponent<{}> = () => {
	return <TeamsByTopics/>
};

export { InfoView };