import React, { useState } from 'react';
import { Calendar, Badge } from 'antd';
import * as moment from 'moment';
import './CalendarStyles.css';
interface StatusType {
  type: 'warning' | 'success' | 'error' | 'default' | 'processing' | undefined;
  content: string;
}

const CalendarView: React.FunctionComponent<{}> = () => {
	const handleOnSelect = (): void => {};

	return (
		<Calendar
			dateCellRender={dateCellRender}
			monthCellRender={monthCellRender}
			onSelect={handleOnSelect}
		/>
	);
};

function getListData(value: moment.Moment) {
	let listData: StatusType[] = [];

	switch (value.date()) {
	case 8:
		listData = [
			{ type: 'warning', content: 'This is warning event.' },
			{ type: 'success', content: 'This is usual event.' }
		];
		break;
	case 10:
		listData = [
			{ type: 'warning', content: 'This is warning event.' },
			{ type: 'success', content: 'This is usual event.' },
			{ type: 'error', content: 'This is error event.' }
		];
		break;
	case 15:
		listData = [
			{ type: 'warning', content: 'This is warning event' },
			{ type: 'success', content: 'This is very long usual event。。....' },
			{ type: 'error', content: 'This is error event 1.' },
			{ type: 'error', content: 'This is error event 2.' },
			{ type: 'error', content: 'This is error event 3.' },
			{ type: 'error', content: 'This is error event 4.' }
		];
		break;
	default:
	}
	return listData || [];
}

function dateCellRender(value: moment.Moment) {
	const listData = getListData(value);

	return (
		<ul className="events">
			{listData.map(item => (
				<li key={item.content}>
					<Badge status={item.type} text={item.content} />
				</li>
			))}
		</ul>
	);
}

function getMonthData(value: moment.Moment) {
	if (value.month() === 8) {
		return 1394;
	}
}

function monthCellRender(value: moment.Moment) {
	const num = getMonthData(value);

	return num ? (
		<div className="notes-month">
			<section>{num}</section>
			<span>Backlog number</span>
		</div>
	) : null;
}
export default CalendarView;
