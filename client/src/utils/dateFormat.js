const yearMonthDate = (date) => {
  const y = parseInt(date.substr(0, 4));
  const m = parseInt(date.substr(4, 2));
  const d = parseInt(date.substr(6, 2));

  return y + '.' + m + '.' + d;
};

export default yearMonthDate;
