import React, { useEffect, useState } from 'react';

import { Calendar, Divider, Modal, Spin, Tag, Typography } from 'antd';
import moment from 'moment';

import { DAY_EVENT_LIST_EMPTY } from '../../../constants/otherConstants';
import calendarService from '../../../api/calendar-service';

import notificationService, { NotificationType } from '../../../service/notification-service';

import './TeamCalendarStyles.css';
import { TeamLearningDaysResponse } from '../../../api/model/team-learning-days-response';


const { Title } = Typography;

const TeamCalendarView: React.FunctionComponent<{}> = () => {
	// onSelect will display a modal to add event or open specific day
	const [modalVisibility, setModalVisibility] = useState<boolean>(false);
	const [selectedDate, setSelectedDate] = useState<moment.Moment | undefined>(
		undefined
	);

	// status type is our example entity
	const [modalListData, setModalListData] = useState<TeamLearningDaysResponse[]>([]);
	const [calendarListData, setCalendarListData] = useState<TeamLearningDaysResponse[]>([]);
	const [isLoading, setLoading] = useState<boolean>(false);

	//component did mount
	useEffect(() => {
		loadData(moment()).then(learningEvents =>
			setCalendarListData(learningEvents)
		);
	}, []);

	function handleOnOk(): void {
		setModalVisibility(false);
	}

	function handleOnCancel (): void {
		setModalVisibility(false);
	}

	function handleSelect(date?: moment.Moment | undefined): void {
		setSelectedDate(date);
		setModalVisibility(true);
		if (date !== undefined) setModalListData(getListData(date));
	}
	// TODO: move DateCellRenderer into its own module when data fetching is implemented
	function DateCellRenderer(value: moment.Moment): React.ReactNode {
		//TODO: Rethink colors of calendar cells
		return (
			<div className="events-cell-wrapper" onClick={() => handleSelect(value)}>
				<ul className="events">
					{calendarListData ? getListData(value).map(item => (
						<li key={item.id}>
							<Tag color={item.learned ? 'green' : 'red'}>
								{item.assignee.name} {item.assignee.surname}
								<br/>
								{item.topic.name}
							</Tag>
						</li>
					)) : null}
				</ul>
			</div>
		);
	}

	function getListData(value: moment.Moment): TeamLearningDaysResponse[] {
		return calendarListData.filter(learningEvent =>
			moment(value.toDate()).isSame(learningEvent.dateTimeAt, 'date'));
	}

	function loadData(value: moment.Moment): Promise<TeamLearningDaysResponse[]> {
		// fetch by year, month, day (or any arguments based on backend requirements)
		// screeconsole.log(value.year(), value.month(), value.date())

		// example data:
		setLoading(true);
		return calendarService.getTeamMonthLearningDays({
			selectedYear: value.format('Y'),
			selectedMonth: value.format('M')
		}).then((response) => {
			setLoading(false);
			return response;
		}).catch((error) => {
			notificationService.notify({
				notificationType: NotificationType.ERROR,
				message: 'Failed to load events',
				description: error.toString()
			});
			setLoading(false);
			return [];
		});
	}

	function getMonthEvents(value: moment.Moment) {
		loadData(value).then((learningEvents) => {
			setCalendarListData(learningEvents);
		});
	}

	return (
		<div>
			<Title level={2} className={'myCalendarTitle'}>Your team calendar</Title>
			<Spin spinning={isLoading} size="large">
				<Calendar
					dateCellRender={DateCellRenderer}
					monthCellRender={renderMonthCell}
					onChange={getMonthEvents}
				/>
			</Spin>
			{renderDateCellModal()}
		</div>
	);

	//TODO: Refactor to component
	function renderDateCellModal(): React.ReactNode {
		return (
			<Modal
				title={
					selectedDate === undefined
						? null
						: selectedDate.format('yyyy-MM-DD')
				}
				visible={modalVisibility}
				onOk={handleOnOk}
				onCancel={handleOnCancel}
				footer={null}
			>
				{renderModalLearningDayData()}
				{
					selectedDate === undefined ? null : calendarListData.length === 0 ? (
						<p>{DAY_EVENT_LIST_EMPTY}</p>
					) : null
				}
			</Modal>
		);
	}

	function renderModalLearningDayData(): React.ReactNode {
		return modalListData.length === 0
			? null
			: modalListData.map(item => (
				<>
					<li key={item.id}>
						<Typography.Paragraph>
							{`Assignee: ${item.assignee.name}
							${item.assignee.surname},
							${item.assignee.role.name}`}
						</Typography.Paragraph>
						<Typography.Paragraph>Topic name: {item.topic.name}</Typography.Paragraph>
						<Typography.Paragraph>Description: {item.topic.description}</Typography.Paragraph>
						<Typography.Paragraph>Comments: {item.comment}</Typography.Paragraph>
					</li>
					<Divider/>
				</>
			));
	}
};
// this method will be changed into a get request

function getMonthData (value: moment.Moment): number | null {
	if (value.month() === 8) {
		return 1394;
	}
	return null;
}

function renderMonthCell(value: moment.Moment): React.ReactNode {
	const num = getMonthData(value);

	return num ? (
		<div className="notes-month">
			<section>{num}</section>
			<span>Backlog number</span>
		</div>
	) : null;
}

export { TeamCalendarView };
