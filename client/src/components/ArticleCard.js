import styled from 'styled-components';
import { Link } from 'react-router-dom';
import { ReactComponent as LikeIcon } from '../assets/image/like.svg';
import { ReactComponent as ViewsIcon } from '../assets/image/views.svg';
import yearMonthDate from '../utils/dateFormat';

const ArticleCardWrapper = styled(Link)`
  display: block;
  width: 323px;
  height: 286px;
  border-radius: 25px;
  background-color: #ffffff;
  transition: 300ms ease-in-out;
  position: relative;

  &:hover {
    box-shadow: 0px 4px 15px rgba(0, 0, 0, 0.2);
    transform: translate(0, -5px);
  }

  &.closed {
    &:after {
      content: '';
      display: block;
      width: 100%;
      height: 100%;
      position: absolute;
      top: 0;
      left: 0;
      border-radius: 25px;
      box-shadow: inset 0 0 0 400px rgba(0, 0, 0, 0.15);
    }
  }

  article {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    width: 100%;
    height: 100%;
    overflow: hidden;
    padding: 37px 33px;
  }

  .article-header {
    margin-bottom: 20px;
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 2;
    overflow: hidden;
    color: var(--black);
  }

  .project-info,
  .hashtags {
    margin-bottom: 4px;
    font-size: 15px;
    line-height: 18px;
    color: var(--grey-dark);
  }

  .project-info {
    font-weight: 500;

    p {
      margin-bottom: 4px;

      span {
        margin-right: 4px;
      }
    }

    p:last-child {
      margin-bottom: 20px;

      span:last-child {
        margin-left: 6px;

        &::before {
          content: '|';
          margin-right: 6px;
        }
      }
    }
  }

  .hashtags {
    font-weight: 400;
  }

  .project-summary {
    margin-bottom: 10px;
  }

  footer {
    display: flex;
    justify-content: space-between;
    color: var(--grey-dark);

    .author {
      width: 100%;
      font-weight: 700;
      color: var(--grey-dark);
    }

    .article-info {
      display: flex;
      font-weight: 400;

      div {
        display: flex;

        svg {
          margin-right: 4px;
        }
      }

      div:first-child {
        margin-right: 4px;
      }
    }
  }
`;

const Tag = styled.li`
  display: inline-block;
  height: 26px;
  margin-right: 7px;
  padding: 4px 15px;
  border-radius: 13px;
  font-weight: 900;
  font-size: 13px;
  color: #fff;
  background-color: var(--purple);

  span {
    vertical-align: middle;
  }
`;

const ArticleCard = ({
  articleId,
  isCompleted,
  title,
  startDay,
  endDay,
  frontend,
  backend,
  hashtags,
  skills,
  memberName,
  heartCount,
  views,
}) => {
  return (
    <ArticleCardWrapper
      to={articleId}
      className={isCompleted === false ? '' : 'closed'}
    >
      <article>
        <h3 className="article-header">{title}</h3>
        <div className="project-info">
          <p>
            <span>개발 기간</span>
            {yearMonthDate(startDay)} - {yearMonthDate(endDay)}
          </p>
          <p className="people-number">
            <span>프론트엔드</span>
            {frontend}명<span>백엔드</span>
            {backend}명
          </p>
        </div>
        <div className="project-summary">
          <p className="hashtags">{hashtags.map((tag) => '#' + tag.name)}</p>
          <ul>
            {skills.map((skill) => (
              <Tag key={skill.skillId}>
                <span>{skill.name}</span>
              </Tag>
            ))}
          </ul>
        </div>
        <footer>
          <div className="author">{memberName}</div>
          <div className="article-info">
            <div>
              <LikeIcon aria-label="좋아요 수" role="img" />
              <span>{heartCount}</span>
            </div>
            <div>
              <ViewsIcon aria-label="조회수" role="img" />
              <span>{views}</span>
            </div>
          </div>
        </footer>
      </article>
    </ArticleCardWrapper>
  );
};

export default ArticleCard;
