import styled from 'styled-components';

const Span = styled.span`
  font-size: 13px;
  color: ${({ disabled, isError }) => handleColor(disabled, isError)};
  pointer-events: none;

  &:disabled {
    color: grey;
  }
`;

const handleColor = (disabled, isError) => {
  if (disabled === true) return 'grey';
  if (isError === true) return 'red';
  return 'var(--purple)';
};

const Message = ({ disabled, isError, text }) => {
  return (
    <Span isError={isError} disabled={disabled} status={'disabled'}>
      {text}
    </Span>
  );
};

export default Message;
