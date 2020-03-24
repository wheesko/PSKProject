import React from 'react';

import {Calendar, Typography} from 'antd';
import './TeamCalendarStyles.css'
import {MY_TEAM_CALENDAR} from "../../../Constants";

const {Title} = Typography;
const TeamCalendarView: React.FunctionComponent<{}> = () => {
		return <>
			<Title level={2} className={"teamCalendarTitle"}>{MY_TEAM_CALENDAR}</Title>
			<Calendar />
		</>;
	}
;
export default TeamCalendarView;