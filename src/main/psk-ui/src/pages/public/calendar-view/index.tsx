import React, { useState } from "react";

import { Calendar, Badge } from "antd";
import * as moment from "moment";
import { StatusType } from "../../../models/status-type-model";

import "./CalendarStyles.css";

const CalendarView: React.FunctionComponent<{}> = () => {
  // onSelect will display a modal to add event or open specific day
  const handleOnSelect = (): void => {};

  return (
    <Calendar
      dateCellRender={dateCellRender}
      monthCellRender={monthCellRender}
      onSelect={handleOnSelect}
    />
  );
};

// in the future, using this function, we will fetch data from the backend
// and return result in a listData type
const getListData = (value: moment.Moment) => {
  // fetch by year, month, day (or any arguments based on backend requirements)
  // console.log(value.year(), value.month(), value.date())

  // example data:
  let listData: StatusType[] = [];
  switch (value.date()) {
    case 8:
      listData = [
        { type: "warning", content: "This is warning event." },
        { type: "success", content: "This is usual event." }
      ];
      break;
    case 10:
      listData = [
        { type: "warning", content: "This is warning event." },
        { type: "success", content: "This is usual event." },
        { type: "error", content: "This is error event." }
      ];
      break;
    case 15:
      listData = [
        { type: "warning", content: "This is warning event" },
        { type: "success", content: "This is very long usual event。。...." },
        { type: "error", content: "This is error event 1." },
        { type: "error", content: "This is error event 2." },
        { type: "error", content: "This is error event 3." },
        { type: "error", content: "This is error event 4." }
      ];
      break;
    default:
  }
  return listData;
};

// responsible for displaying items in each cell
const dateCellRender = (value: moment.Moment): React.ReactNode => {
  // value is the specific day
  // get data from backend about each day
  const listData = getListData(value);

  // and display
  return (
    <ul className="events">
      {listData.map(item => (
        <li key={item.content}>
          <Badge status={item.type} text={item.content} />
        </li>
      ))}
    </ul>
  );
};

const getMonthData = (value: moment.Moment) => {
  if (value.month() === 8) {
    return 1394;
  }
};

const monthCellRender = (value: moment.Moment) => {
  const num = getMonthData(value);

  return num ? (
    <div className="notes-month">
      <section>{num}</section>
      <span>Backlog number</span>
    </div>
  ) : null;
};
export default CalendarView;
