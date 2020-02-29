package by.tananushka.project.command;

import by.tananushka.project.command.impl.cinema.CreateCinemaCommand;
import by.tananushka.project.command.impl.cinema.DeleteCinemaCommand;
import by.tananushka.project.command.impl.cinema.PrepareCinemaCreationCommand;
import by.tananushka.project.command.impl.cinema.PrepareCinemaDeletionCommand;
import by.tananushka.project.command.impl.cinema.PrepareCinemaEditionCommand;
import by.tananushka.project.command.impl.cinema.PrepareCinemaRestorationCommand;
import by.tananushka.project.command.impl.cinema.PrepareCinemaUpdateCommand;
import by.tananushka.project.command.impl.cinema.RestoreCinemaCommand;
import by.tananushka.project.command.impl.cinema.UpdateCinemaCommand;
import by.tananushka.project.command.impl.client.EditClientCommand;
import by.tananushka.project.command.impl.client.FindActiveClientsCommand;
import by.tananushka.project.command.impl.client.FindAllClientsCommand;
import by.tananushka.project.command.impl.client.UpdateClientCommand;
import by.tananushka.project.command.impl.common.AuthenticationCommand;
import by.tananushka.project.command.impl.common.ChangeLocaleCommand;
import by.tananushka.project.command.impl.common.EmailConfirmationCommand;
import by.tananushka.project.command.impl.common.LogoutCommand;
import by.tananushka.project.command.impl.common.NoSuchCommand;
import by.tananushka.project.command.impl.common.RegistrationCommand;
import by.tananushka.project.command.impl.common.RegistrationPrepareCommand;
import by.tananushka.project.command.impl.common.SendMessageCommand;
import by.tananushka.project.command.impl.common.SendPasswordCommand;
import by.tananushka.project.command.impl.film.CreateFilmCommand;
import by.tananushka.project.command.impl.film.DeleteFilmCommand;
import by.tananushka.project.command.impl.film.EditFilmCommand;
import by.tananushka.project.command.impl.film.FindActiveFilmsCommand;
import by.tananushka.project.command.impl.film.FindFilmToDeleteCommand;
import by.tananushka.project.command.impl.film.FindFilmsCommand;
import by.tananushka.project.command.impl.film.PrepareFilmCreationCommand;
import by.tananushka.project.command.impl.film.SetImageCommand;
import by.tananushka.project.command.impl.film.UpdateFilmCommand;
import by.tananushka.project.command.impl.genre.CreateGenreCommand;
import by.tananushka.project.command.impl.genre.PrepareGenreCreationCommand;
import by.tananushka.project.command.impl.genre.PrepareGenreEditionCommand;
import by.tananushka.project.command.impl.genre.PrepareGenreUpdateCommand;
import by.tananushka.project.command.impl.genre.UpdateGenreCommand;
import by.tananushka.project.command.impl.manager.AppointManagerCommand;
import by.tananushka.project.command.impl.manager.EditManagerCommand;
import by.tananushka.project.command.impl.manager.FindActiveManagersCommand;
import by.tananushka.project.command.impl.manager.FindAllManagersCommand;
import by.tananushka.project.command.impl.manager.UpdateManagerCommand;
import by.tananushka.project.command.impl.show.CreateShowCommand;
import by.tananushka.project.command.impl.show.DeleteShowCommand;
import by.tananushka.project.command.impl.show.EditShowCommand;
import by.tananushka.project.command.impl.show.FindShowToDeleteCommand;
import by.tananushka.project.command.impl.show.FindShowsCommand;
import by.tananushka.project.command.impl.show.PrepareShowCreationCommand;
import by.tananushka.project.command.impl.show.UpdateShowCommand;
import by.tananushka.project.command.impl.user.DeleteAccountCommand;
import by.tananushka.project.command.impl.user.EditProfileCommand;
import by.tananushka.project.command.impl.user.EditUserCommand;
import by.tananushka.project.command.impl.user.FindAllUsersCommand;
import by.tananushka.project.command.impl.user.UpdateProfileCommand;
import by.tananushka.project.command.impl.user.UpdateUserCommand;
import by.tananushka.project.command.impl.user.ViewProfileCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.EnumMap;
import java.util.Map;
//import by.tananushka.project.command.impl.show.EditShowCommand;

public class CommandProvider {

	private static final CommandProvider instance = new CommandProvider();
	private static Logger log = LogManager.getLogger();
	private Map<CommandName, Command> commands = new EnumMap<>(CommandName.class);

	private CommandProvider() {
		commands.put(CommandName.AUTHENTICATION, new AuthenticationCommand());
		commands.put(CommandName.CHANGE_LOCALE, new ChangeLocaleCommand());
		commands.put(CommandName.VIEW_PROFILE, new ViewProfileCommand());
		commands.put(CommandName.NO_SUCH_COMMAND, new NoSuchCommand());
		commands.put(CommandName.REGISTRATION, new RegistrationCommand());
		commands.put(CommandName.REGISTRATION_INITIALIZE, new RegistrationPrepareCommand());
		commands.put(CommandName.LOGOUT, new LogoutCommand());
		commands.put(CommandName.EMAIL_CONFIRMATION, new EmailConfirmationCommand());
		commands.put(CommandName.SEND_MESSAGE, new SendMessageCommand());
		commands.put(CommandName.FILMS_AVAILABLE, new FindFilmsCommand());
		commands.put(CommandName.FILMS_ACTIVE, new FindActiveFilmsCommand());
		commands.put(CommandName.SHOWS_AVAILABLE, new FindShowsCommand());
		commands.put(CommandName.EDIT_FILM, new EditFilmCommand());
		commands.put(CommandName.EDIT_SHOW, new EditShowCommand());
		commands.put(CommandName.UPDATE_FILM, new UpdateFilmCommand());
		commands.put(CommandName.UPDATE_SHOW, new UpdateShowCommand());
		commands.put(CommandName.FILM_TO_DELETE, new FindFilmToDeleteCommand());
		commands.put(CommandName.DELETE_FILM, new DeleteFilmCommand());
		commands.put(CommandName.SHOW_TO_DELETE, new FindShowToDeleteCommand());
		commands.put(CommandName.DELETE_SHOW, new DeleteShowCommand());
		commands.put(CommandName.SET_IMAGE, new SetImageCommand());
		commands.put(CommandName.CREATE_FILM, new PrepareFilmCreationCommand());
		commands.put(CommandName.ADD_FILM, new CreateFilmCommand());
		commands.put(CommandName.CREATE_SHOW, new PrepareShowCreationCommand());
		commands.put(CommandName.ADD_SHOW, new CreateShowCommand());
		commands.put(CommandName.CREATE_CINEMA, new PrepareCinemaCreationCommand());
		commands.put(CommandName.ADD_CINEMA, new CreateCinemaCommand());
		commands.put(CommandName.CREATE_GENRE, new PrepareGenreCreationCommand());
		commands.put(CommandName.ADD_GENRE, new CreateGenreCommand());
		commands.put(CommandName.DELETE_CINEMA, new PrepareCinemaDeletionCommand());
		commands.put(CommandName.REMOVE_CINEMA, new DeleteCinemaCommand());
		commands.put(CommandName.RESTORE_CINEMA, new PrepareCinemaRestorationCommand());
		commands.put(CommandName.RETURN_CINEMA, new RestoreCinemaCommand());
		commands.put(CommandName.EDIT_CINEMA, new PrepareCinemaEditionCommand());
		commands.put(CommandName.PREPARE_CINEMA_UPDATE, new PrepareCinemaUpdateCommand());
		commands.put(CommandName.UPDATE_CINEMA, new UpdateCinemaCommand());
		commands.put(CommandName.EDIT_GENRE, new PrepareGenreEditionCommand());
		commands.put(CommandName.PREPARE_GENRE_UPDATE, new PrepareGenreUpdateCommand());
		commands.put(CommandName.UPDATE_GENRE, new UpdateGenreCommand());
		commands.put(CommandName.FIND_ACTIVE_CLIENTS, new FindActiveClientsCommand());
		commands.put(CommandName.FIND_ALL_CLIENTS, new FindAllClientsCommand());
		commands.put(CommandName.EDIT_CLIENT, new EditClientCommand());
		commands.put(CommandName.UPDATE_CLIENT, new UpdateClientCommand());
		commands.put(CommandName.FIND_ACTIVE_MANAGERS, new FindActiveManagersCommand());
		commands.put(CommandName.FIND_ALL_MANAGERS, new FindAllManagersCommand());
		commands.put(CommandName.EDIT_MANAGER, new EditManagerCommand());
		commands.put(CommandName.UPDATE_MANAGER, new UpdateManagerCommand());
		commands.put(CommandName.FIND_ALL_USERS, new FindAllUsersCommand());
		commands.put(CommandName.EDIT_USER, new EditUserCommand());
		commands.put(CommandName.UPDATE_USER, new UpdateUserCommand());
		commands.put(CommandName.APPOINT_MANAGER, new AppointManagerCommand());
		commands.put(CommandName.SEND_PASSWORD, new SendPasswordCommand());
		commands.put(CommandName.UPDATE_PROFILE, new UpdateProfileCommand());
		commands.put(CommandName.EDIT_PROFILE, new EditProfileCommand());
		commands.put(CommandName.DELETE_USER, new DeleteAccountCommand());
	}

	public static CommandProvider getInstance() {
		return instance;
	}

	public Command getCommand(String commandName) {
		Command command = commands.get(CommandName.NO_SUCH_COMMAND);
		if (commandName != null) {
			try {
				CommandName name = CommandName
								.valueOf(commandName
												.replace("-", "_")
												.toUpperCase());
				command = commands.get(name);
			} catch (IllegalArgumentException e) {
				log.error("No such command: {}", commandName, e);
			}
		}
		return command;
	}
}
